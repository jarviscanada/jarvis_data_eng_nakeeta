#!/bin/bash
# assign CLI arguments to variables
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5
export PGPASSWORD=$psql_password

# parse host hardware specifications using bash cmds
lscpu=`lscpu`
vm= `vmstat`
disk= `df`
timestamp=$(date '+%Y-%m-%d %T')
memory_free=$(cat /proc/meminfo | grep "MemFree"| egrep -o "[0-9]{2,}"| xargs)
cpu_idle=$(echo "$vm" -t | awk '{ print $15}' |tail -1 | xargs)
cpu_kernel=$(echo "$vm" -t | awk '{print $14}' | tail -1 | xargs)
disk_io=$(echo "$vm" -d| awk '{print $10}'| tail -1 |xargs)
disk_available=$(echo "$disk" -BM | awk '{print $4}'|head -6| tail -1| egrep -o '[0-9]+'| xargs)



# insert staemaent
in_state=" INSERT INTO host_usage (timestamp,memory_free, cpu_idle, cpu_kernel,disk_io,disk_available)
VALUES ('"$timestamp"', '"$memory_free"','"$cpu_idle"','"$cpu_kernel"','"$disk_io"','"$disk_available"')"

# Connect to PSQL instance
psql psql -h $psql_host -U $psql_user  -d $db_name -c "$in_state"

exit $?
