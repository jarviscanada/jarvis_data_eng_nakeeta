#!/bin/bash
## assign CLI arguments to variables
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

export PGPASWORD=$psql_password

## parse host hardware specifications using bash cmds
cpu_list=`lscpu`
vm_list=`vmstat`
disk=`df`
hostname=$(hostname -f)
host_id=$(echo "$(psql -h localhost -U postgres -d host_agent -c "select id from host_info where hostname='$hostname'" | head -3 | tail -1 | xargs)")
timestamp=$(date '+%Y-%m-%d %T')
memory_free=$(echo "$vm_list" -t|  awk '{print $4}' | tail -1 | egrep -o "[0-9]{2,}" | xargs)
cpu_idle=$(echo "$vm_list" -t | awk '{ print $15}' | tail -1 | xargs)
cpu_kernel=$(echo "$vm_list" -t | awk '{print $14}' | tail -1 | xargs)
disk_io=$(echo "$vm_list" -d| awk '{print $10}'| tail -1 |xargs)
disk_available=$(echo "$disk" -BM | awk '{print $4}' | head -6 | tail -1 | egrep -o '[1-9]+'  | xargs)


## insert staemaent
insert_statement="INSERT INTO host_usage (timestamp, host_id, memory_free, cpu_idle, cpu_kernel,disk_io, disk_available) VALUES ('$timestamp', '$host_id', '$memory_free', '$cpu_idle', '$cpu_kernel','$disk_io', '$disk_available');"

##insert command
psql -h $psql_host -U $psql_user -d $db_name -c "$insert_statement"

exit 0
