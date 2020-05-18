
ption=$1
username=$2
password=$3

systemctl status docker || systemctl start docker
if [[ $option = "create" ]]; then
  if [[ "$user_name" == "0" ]] || [[ "$password" == "0" ]]; then
    echo "Error : user name or password not passed through CLI"
    exit $?
  fi

  if [[ $(docker container ls -a -f name=jrvs-psql | wc -l) -ge 2 ]]; then
        echo "Error: Container is already created"
    else
      docker run --name jrvs-psql -e POSTGRES_PASSWORD=$password -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 $username
      exit $?
    fi
fi

if [[ $option = "start" ]]; then
  if [[ $(docker container ls -a -f name=jrvs-psql | wc -l) -ge 2 ]]; then
   docker container start jrvs-psql
   echo "Successfully started"
    else
   echo "jrvs-psql container has not been created"
 fi
   exit $?
fi

if [[ $option = "stop" ]]; then
   docker container stop jrvs-psql
   echo "Successfully stopped"
   exit $?
fi
echo "Error: Invaid Entry"
exit 0
