"# Burrito-App" 

To clear the database:
MainActivity.java > onCreate() > writeToFile(data)
	remove comment from the writeToFile(data) 

Start the app, close it.

Re-insert the comment on MainActivity.java > onCreate() > writeToFile(data)

Now that the database is empty we must make some changes listed below... until we have at least 1 user added to database from the completing register account screen:

AccountFragment.java > onClick() > userList = readFromFile(userList)
	add comment to this line to avoid app crash because userList is empty after flush

AccountFragment.java > onClick() > nextAvailableMemberID = init_memberID + userList.size();
	add comment to this line to avoid app crash because userList is empty after flush


