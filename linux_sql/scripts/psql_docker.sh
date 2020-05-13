#!/bin/bash
option= $1
user_name= $2
password= $3

systemctl status docker || systemctl start docker
# case create
if [$option =="create" ]; then
  if [[ "$user_name" == "0" ]] || [[ "$password" == "0" ]]; then
    echo "Error : user name or password not passed through CLI"
    exit 1
  fi
  # if container exists print error message
  if [$user_name =="jarvis-psql" ]; then
    echo "Error: Container is already created"
    exit 1
      #otherwise create container
      else
        docker run --name jrvs-psql -e POSTGRES_PASSWORD=${password} -e POSTGRES_USER=${user_name} -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
        exit $?
    fi

fi
# case start
if [$option =="start" ];then
  # check if container is already created, if so start container
  if [[ $(docker container ls -a -f name=jrvs-psql |wc -l) -ge 2]]; then
    docker container start jrvs-psql
    exit$?
  fi
fi
#case stop
if [$option =="stop" ]; then
    docker container stop jrvs-psql
    exit$?
  fi

#case invalid
echo "Error: invaid entry"
exit 0
