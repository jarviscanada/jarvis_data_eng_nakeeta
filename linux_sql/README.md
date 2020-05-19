# Linux Cluster Monitoring Agent
## Introduction
The Linux Cluster Administration (LCA) Team has been requested with the responsibility of managing a Linux cluster. The cluster consists of 10 nodes/servers, all running on CentOS7 and are internally connected by a switch. Cluster Monitor agent is an internal tool that monitors server clusters by recording CPU and memory hardware specifications of each node/server. The collected data is then stored in a local PostgreSQL database. This allows the LCA team the  see the usage and activity of each node/server, which will be informative when making business decisions.
## Architecture and Design
![Untitled Diagram](https://user-images.githubusercontent.com/64488220/81887214-9e1e1680-956c-11ea-8fb2-5055bb4d5c9d.png)
The diagram above illustrates a simmilar architecture design using 3 nodes  <br /> <br /> 
### Database tables
#### host_info.sh
host_info stores hardware specifications of each node/server of the host machine <br />  <br /> 

| Name             | Type      | Description                               |
|------------------|-----------|-------------------------------------------|
| id               | SERIAL    | Primary key, ID for host machine          |
| hostname         | VARCHAR   | Host machine name                         |
| cpu_number       | INT       | Number of cpu cores                       |
| cpu_architecture | VARCHAR   | CPU architecture of host machine          |
| cpu_model        | VARCHAR   | Model name and type of CPU                |
| cpu_mhz          | DECIMAL   | The speed of microprocessors Units:GHz    |
| L2_cache         | INT       | L2 cache of host machine Units :KB        |
| total_mem        | INT       | Total memory of host michine units:KB     |
| timestamp        | TIMESTAMP | Timestamp format 2020-05-29 17:49:53      |

#### host_usage.sh
host_usage.sh collects Linux resource usage data <br />  <br /> 

| Name             | Type      | Description                               |
|------------------|-----------|-------------------------------------------|
| id               | SERIAL    | REFERENCES id host_info                   |
| host_id          | SERIAL    | Primary KEY                               |
| memory_free      | INT       | Free memory, Units:MB                     |
| cpu_idle         | INT       | CPU idle in precentage                    |
| cpu_kernel       | INT       | CPU kernel usage in precentage            |
| disk_io          | INT       | Number disk in IO                         |
| disk_available   | INT       | Disk space avalible                       |
| timestamp        | TIMESTAMP | Timestamp format 2020-05-29 17:49:53      |

## Usage
### psql_docker.sh
psql_docker has 3 input options when executing 
#### Create 
`./psql_docker.sh [create] [db_username] [db_password]`<br /> 
Note: if User name or password are not provied error message will be displayed
#### Start
`./psql_docker.sh [Start] [db_username] [db_password]`<br /> 
If container is already created then docker container will start  
#### Stop
`./psql_docker.sh [Stop] [db_username] [db_password]`<br /> 
Stops  container
### ddl.sql
Creates two Tables host_info and host_usage. 
how to run the script:<br />  <br /> 
`./psql -h [hostname] -U [username] -p [port number] -c ddl.sql`
### host_info.sh
Collects all hardware specifications and inputs the values into ddl.sql
how to run the script:<br />  <br /> 
`./host_info.sh [hostname] [database name] [username] [user password]`
### host_usage.sh
Collects all hardware specifications and inputs the values into ddl.sql
how to run the script:<br />  <br /> 
`./host_usage.sh [hostname] [database name] [username] [user password]`
### Real time monitoring 
Using crontab execute `host_usage.sh` every minuite <br />  <br /> 
Edit crontab `crontab -e` <br /> 
Add crontab<br />
`* * * * * bash <path_to_project>/linux_sql/scripts/host_usage.sh [psql_host] [psql_port] [db_name] [psql_user] [psql_passwd] >> /tmp/host_usage.log`<br /> 
To check all running crontabs `crontab -l`
## Improvements 
1) Combine host_info and host_usage into one table
2) Handle hardware updates 
3) Fault tolerance
