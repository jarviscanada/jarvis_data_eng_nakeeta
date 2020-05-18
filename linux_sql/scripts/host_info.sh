#!/bin/bash
##assign CLI arguments to variables
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5
export PGPASSWORD=$psql_password

# parse host hardware specifications using bash cmds
cpu_list=`lscpu`
hostname=$(hostname -f)
cpu_number=$(echo "$cpu_list"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$cpu_list" | grep "Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$cpu_list" | grep "Model\sname:"| awk -F ": " '{print $2}' | xargs)
cpu_mhz=$(echo "$cpu_list" | grep  "CPU\sMHz:"| awk -F ": " '{print $2}' | xargs )
l2_cache=$(echo "$cpu_list" | grep "L2\scache:" | egrep -o "[0-9]{2,}" |xargs)
total_mem=$(cat /proc/meminfo | grep "MemTotal"| egrep -o "[0-9]{2,}" |xargs)
timestamp=$(date '+%Y-%m-%d %T')

##insert staemaent
stat="INSERT INTO host_info (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz,L2_cache, total_mem, timestamp) VALUES ('$hostname', '$cpu_number', '$cpu_architecture', '$cpu_model', '$cpu_mhz','$l2_cache', '$total_mem', '$timestamp');"

##executing insert command
psql -h $psql_host -U $psql_user  -d $db_name -c "$stat"

exit 0
