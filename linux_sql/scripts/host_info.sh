#!/bin/bash
# assign CLI arguments to variables
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5
export PGPASSWORD=$psql_password

# parse host hardware specifications using bash cmds
lscpu='lscpu'
hostname=$(hostname -f)
cpu_number=$(echo "$lscpu"  | egrep "^CPU\(s\):" |awk '{print $2}'| xargs)
cpu_architecture=$(echo "$lscpu" | grep "Architecture:" |awk '{print $2}'| xargs)
cpu_model=$(echo "$lscpu" | grep "Model\sname:"|awk -F ": " '{print $2}'| xargs)
cpu_mhz=$(echo "$lscpu" | grep  "CPU\sMHz:"|awk -F ": " '{print $2}'| xargs )
l2_cache=$(echo "$lscpu" | grep "L2\scache:" | egrep -o "[0-9]{2,}"|xargs)
total_mem=$(cat /proc/meminfo | grep "MemTotal"| egrep -o "[0-9]{2,}"|xargs)
timestamp=$(date '+%Y-%m-%d %T')

# insert staemaent
in_state="INSERT INTO host_info (hostname,cpu_number, cpu_architecture, cpu_model, cpu_mhz,l2_cache,total_mem,timestamp)
VALUES ('"$hostname"', '"$cpu_num"','"$cup_architecture"','"$cpu_model"','"$cpu_mhz"','"$l2_cache"', '"$total_mem"','"$timestamp"')"

# connect to PSQL instance
psql psql -h $psql_host -U $psql_user  -d $db_name -c "$in_state"

exit $?
