#!/bin/sh

host=localhost
port=8080
api_root=""
endpoint="/users"
header1="Content-Type: application/json"
header2="Accept: application/json"
RED='\033[0;31m' 
GREEN='\033[0;32m' 
NC='\033[0m' # No Color
verbose=false
if [ "$1" = "verbose" ] ; then
	verbose=true
fi

# function for testing API with 'H' Header and Request 'd' data, e.g. POST, PUT, PATCH methods
# It takes as its arg a function to generate it's request body data
testHd() {
	response=$(curl --request $method \
		--url http://${host}:${port}${api_root}${endpoint} \
		--header "$header1" \
		--header "$header2" \
		--silent \
		--write-out '%{http_code}' \
		--data "$($1)")
	status=$(echo -n $response | tail -c 4)
	if [ "$status" = "200" ]; then
		echo ${GREEN}✔  $method $endpoint $NC
	else
		echo ${RED}✘  $method $endpoint $NC
	fi
	if [ "$verbose" = true ] ; then
		echo
		echo REQUEST:
		echo "curl --request $method \\"
		echo "     --url http://${host}:${port}${api_root}${endpoint} \\"
		echo "     --header \"$header1\" \\"
		echo "     --header \"$header2\" \\"
		echo "     --data \\"
		echo $($1) | python -m json.tool
		echo
		echo RESPONSE:
		echo ${response%???} | python -m json.tool
		echo
	fi
}

# function for testing API without 'H' Header and Request 'd' data, e.g. GET and DELETE methods
test() {
	response=$(curl --request $method \
		--url ${host}:${port}${api_root}${endpoint} \
		--silent \
		--write-out '%{http_code}')
	status=$(echo -n $response | tail -c 4)
	if [ "$status" = "200" ]; then
		echo ${GREEN}✔  $method $endpoint $NC
	else
		echo ${RED}✘  $method $endpoint $NC
	fi
	if [ "$verbose" = true ] ; then
		echo
		echo REQUEST:
		echo "curl --request $method \\"
		echo "     --url ${host}:${port}${api_root}${endpoint}"
		echo
		echo RESPONSE:
		echo ${response%???} | python -m json.tool
		echo
	fi
}


credentials() {
	cat <<EOF
{
  "credentials": {
    "username": "$username",
    "password": "$password"
  }
}
EOF
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

content_credentials() {
	cat <<EOF
{
  "content": "$tweet",
  "credentials": {
    "username": "$username",
    "password": "$password"
  }
}
EOF
}

echo "Testing REST API endpoints at ${host}:${port}"
echo
echo "  (green means response == 200, red means response != 200)"
echo "  Try \"./apitest.sh verbose\" to see request and response"
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

username=john
password=john1
email=john@ex.com
firstName=John
lastName=Doe
phone=3456789012
testHd credentials_profile

method=GET
endpoint=/users/@john
test

method=DELETE
testHd credentials

method=GET
endpoint=/users
test

method=GET
endpoint=/validate/username/exists/@bob
test
endpoint=/validate/username/available/@henry
test

method=POST
endpoint=/users/@alice/follow
username=bob
password=bob1
testHd credentials

method=GET
endpoint=/users/@bob/following
test

method=GET
endpoint=/users/@alice/followers
test

method=POST
endpoint=/users/@alice/unfollow
testHd credentials

method=GET
endpoint=/users/@bob/tweets
test

method=POST
tweet="Here is my first tweet!"
endpoint=/tweets
testHd content_credentials
tweet="Here is my second with #myhashtag"
testHd content_credentials
tweet="Hey @alice did you see my tweet?"
testHd content_credentials

