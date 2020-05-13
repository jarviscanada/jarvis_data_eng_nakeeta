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
cpu_number=$(echo "$lscpu"  | grep "^CPU\(s\):" | xargs)
cpu_architecture=$(echo "$lscpu" | grep "Architecture:" | xargs)
cpu_model=$(echo "$lscpu" | grep "Model\sname:"| xargs)
cpu_mhz=$(echo "$lscpu" | grep "CPU\sMHz:"| xargs )
l2_cache=$(echo "$lscpu" | grep "L2\scache:" | xargs)
total_mem=$(cat /proc/meminfo | grep "MemTotal")
timestamp=$(date +'%Y12%d %H:%M')

# insert staemaent
in_state="INSERT INTO host_info (hostname,cpu_number, cpu_architecture, cpu_model, cpu_mhz,l2_cache,total_mem,timestamp) 
VALUES ('"$hostname"', '"$cpu_num"','"$cup_architecture"','"$cpu_model"','"$cpu_mhz"','"$l2_cache"', '"$total_mem"','"$timestamp"')"

# connect to PSQL instance
psql -h $psql_host -p $psql_port -U $db_name -d $psql_user -W $psql_password -c "$in_state"

exit 0
