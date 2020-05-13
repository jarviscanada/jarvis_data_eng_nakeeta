er_name
$password

systemctl status docker || systemctl start docker
# case create
if [$1 =="create" ]; then
  if [[ "$user_name" == "0" ]] | [[ "$password" == "0" ]]; then
    echo "Error : user name or password not passed through CLI"
    exit
  fi

  if [$user_name =="jarvis-psql" ]; then
    echo "Error: Container is already created"
    exit
  #create volume
      else
        docker run --name jrvs-psql -e POSTGRES_PASSWORD=${password} -e POSTGRES_USER=${user_name} -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
        exit $1
    fi
fi
# case start
if [$1 =="start" ];then
  docker container start jrvs-psql
  exit$?
fi
#case stop
if [$1 =="stop" ]; then
    docker container stop jrvs-psql
    exit$?
fi
#case invalid
if  [[ "$1" -ne "create" ]] &&  [[ "$1" -ne "start" ]] &&  [[ "$1" -ne "stop" ]]; then
  echo "Error: invaid entry"
  exit 1
fi
