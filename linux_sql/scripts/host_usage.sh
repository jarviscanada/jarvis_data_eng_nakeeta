ssign CLI arguments to variables
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5
export PGPASSWORD=$psql_password

# parse host hardware specifications using bash cmds
specs='lscpu'
vm= 'vmstat'
disk= 'df'
timestamp=$(date +'%Y12%d %H:%M')
memory_free=$(cat /proc/meminfo | grep "MemFree"| xargs)
cpu_idle=$(echo "$vm" -t | awk '{ print $15}' |tail -1 | xargs)
cpu_kernel=$(echo "$lscpu"  | grep "^CPU\(s\):" | xargs)
disk_io=$(echo "$vm" -d| awk '{print $10}'| tail -1 |xargs)
disk_available=$(echo "$disk"-BM | awk '{print $4}'|tail -1| xargs)

# insert staemaent
in_state=" INSERT INTO host_usage (timestamp,memory_free, cpu_idle, cpu_kernel,disk_io,disk_available)
VALUES ('"$timestamp"', '"$memory_free"','"$cpu_idle"','"$cpu_kernel"','"$disk_io"','"$disk_available"')"

# connect to PSQL instance
psql -h $psql_host -p $psql_port -U $db_name -d $psql_user -W $psql_password -c "$in_state"

exit 0
