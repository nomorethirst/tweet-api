#!/bin/sh

host=localhost
port=8080
api_root=""
endpoint="/users"
header=Content-Type:application/json
RED='\033[0;31m' 
GREEN='\033[0;32m' 
NC='\033[0m' # No Color
verbose=false
if [ $1 = verbose ] ; then
	verbose=true
fi

# function for testing API with 'H' Header and Request 'd' data, e.g. POST, PUT, PATCH methods
# It takes as its arg a function to generate it's request body data
testHd() {
	response=$(curl --request $method \
		--url ${host}:${port}${api_root}${endpoint} \
		--header $header \
		--silent \
		--write-out '%{http_code}' \
		--data "$($1)")
	response_code=$(echo -n $response | tail -c 3)
	if [ "$verbose" = true ] ; then
		echo
		echo curl --request $method \
			--url ${host}:${port}${api_root}${endpoint} \
			--header $header \
			--silent \
			--write-out '%{http_code}' \
			--data "$($1)"
		echo $response_code
	fi
	if [ "$response_code" = "200" ]; then
		echo ${GREEN}✔  $method $endpoint $NC
	else
		echo ${RED}✘  $method $endpoint $NC
	fi
}

# function for testing API without 'H' Header and Request 'd' data, e.g. GET and DELETE methods
test() {
	response=$(curl --request $method \
		--url ${host}:${port}${api_root}${endpoint} \
		--silent \
		--write-out '%{http_code}')
	response_code=$(echo -n $response | tail -c 3)
	if [ "$verbose" = true ] ; then
		echo
		echo curl --request $method \
			--url ${host}:${port}${api_root}${endpoint} \
			--silent \
			--write-out '%{http_code}'
		echo $response_code
	fi
	if [ "$response_code" = "200" ]; then
		echo ${GREEN}✔  $method $endpoint $NC
	else
		echo ${RED}✘  $method $endpoint $NC
	fi
}


credentials_profile() {
	cat <<EOF
{
  "credentials": {
    "username": "$username",
    "password": "$password"
  },
  "profile": {
    "email": "$email",
    "firstName": "$firstName",
    "lastName": "$lastName",
    "phone": "$phone"
  }
}
EOF
}

echo "Testing CRUD functionality of REST API at ${host}:${port}"
echo


# CREATE A FEW USERS
method=POST
endpoint=/users

username=bob
password=bob1
email=bob@ex.com
firstName=Bob
lastName=Smith
phone=1234567890
testHd credentials_profile

username=alice
password=alice1
email=alice@ex.com
firstName=Alice
lastName=Jones
phone=2345678901
testHd credentials_profile

method=GET
endpoint=/users/@bob
test

method=DELETE
endpoint=/users/@alice
test

method=GET
endpoint=/users
test
