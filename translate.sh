#!/bin/bash
echo "---- Welcome to English to French translator ----"

targetLanguage="fr"
sourceLanguage="en"

echo "Plase type the sentence in English that you want to translate in French"
read text


Uri="https://translate.googleapis.com/translate_a/single?client=gtx&sl=${sourceLanguage}&tl=${targetLanguage}&dt=t&q=${text}"
Uri=$( printf "%s\n" "$Uri" | sed 's/ /%20/g' ) # Hande white spaces

# Make GET request to api and check the response code
responseCode=$(curl -s -o /dev/null -w "%{http_code}" $Uri)

if [ $responseCode -eq 200 ];	#
then
	response=$(curl -s $Uri)
	IFS='\"' read -a array <<< "${response}"   # converto response in array
	if [ "${array[1]}" != "en"  ]
	then
		echo "The translation in French is:"
		echo "${array[1]}"		  # Print the translated version
	else
		echo "You have not typed a sentence!" # If user have typed only spaces
	fi
else
 	echo "Error with GET request"
fi
