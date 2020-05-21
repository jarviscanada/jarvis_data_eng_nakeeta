# Linux Cluster Monitoring Agent
## Introduction
The Linux Cluster Administration (LCA) Team has been assigned with the responsibility of managing a Linux cluster. The cluster consists of 10 nodes/servers all running on CentOS7 and are internally connected by a switch and can communicate through internal IPv4 addresses. The LCA team needs record the hardware specifications of each node and monitor node resource usage, such CPU and memory usage. The collected data will be in realtime and should be stored in a RDBMS database. The LCA team will use the usage and activity data of each node/server when making future business/ resource planning decisions. <br />
## Overview 
The Cluster monitoring solution presented below is minimum viable product (MVP), all testing has been conducted on a single machine rather then a cluster.  The collected data will be stored in a PostgreSQL database. All required scripts to start, stop and create a PostgreSQL instance in a Docker container is provided.  Scripts to collect and store hardware specification and usage data in the PostgreSQL instance are also provided in this solution.<br />
## Architecture and Design
![My Image](./assets/my_image.png)
The diagram above illustrates a three node cluster internally conneacted by a switch <br /> 
### Database tables
The PostgreSQL  database `host_agent`  contains two tables `host_info` and `host_usage`. <br /> 

`host_info`stores hardware specifications of each node/server of the host machine. This sloution assumes hardware specifications are constant and do not change.<br/>

| Name             | Type      | Description                               |
|------------------|-----------|-------------------------------------------|
| id               | SERIAL    | Primary key, Unique ID for host machine   |
| hostname         | VARCHAR   | Host machine name                         |
| cpu_number       | INT       | Number of cpu cores                       |
| cpu_architecture | VARCHAR   | CPU architecture of host machine          |
| cpu_model        | VARCHAR   | Model name and type of CPU                |
| cpu_mhz          | DECIMAL   | The speed of microprocessors Units:GHz    |
| L2_cache         | INT       | L2 cache of host machine Units :KB        |
| total_mem        | INT       | Total memory of host michine units:KB     |
| timestamp        | TIMESTAMP | UTC Timestamp format 2020-05-29 17:49:53  |
<br/>
`host_usage.sh`
host_usage.sh collects the following usage information by each node every minute, this is done inorder to keep resource usage information up-to-date and to track usage over time.  <br />  <br /> 

| Name             | Type      | Description                               |
|------------------|-----------|-------------------------------------------|
| id               | SERIAL    | REFERENCES id host_info                   |
| host_id          | SERIAL    | Primary KEY                               |
| memory_free      | INT       | Free memory, Units:MB                     |
| cpu_idle         | INT       | CPU idle in precentage                    |
| cpu_kernel       | INT       | CPU kernel usage in precentage            |
| disk_io          | INT       | Number disk in IO                         |
| disk_available   | INT       | Disk space avalible                       |
| timestamp        | TIMESTAMP | TUC Timestamp format 2020-05-29 17:49:53      |

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
Using crontab execute `host_usage.sh` every minuite <br /> 
```
#Edit crontab
crontab -e
# Add thefollowing code in the crontab flie 
* * * * * bash /home/centos/dev/jrvs/bootcamp/linux_sql/host_agent/scripts/host_usage.sh localhost 5432 host_agent postgres password > /tmp/host_usage.log

#To check all running crontabs
crontab -l
```
## Improvements 
1) Combine host_info and host_usage into one table
2) Handle hardware updates 
3) Fault tolerance
