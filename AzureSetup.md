# VM and Tomcat server setup
1. Create a Resource Group on Azure
2. Create a virtual machine for that resource group on Azure. I used the settings we chose for our demo VMs with Brian.
3. SSH into the VM through your cli
	1. select "connect" from the VM's page in Azure
	2. select SSH
	3. copy the command from the right, then paste it into the CLI, delete the protion of the command from -i through .pem.
	   The result should look something like:	   `ssh <username>@<ip-address>`
	4. provide the password you made during the VM creation process.
4. Install Tomcat on the VM by following this guide with some modifications: https://www.redswitches.com/blog/install-apache-tomcat-on-ubuntu/. I will list the modifications here.
	1. Setup the Tomcat User step:
		1. Save yourself the headache and don't create a tomcat user.
	2. "Update the Tomcat User Permissions" step
		1. don't use tomcat in the chown command, use whatever your username is instead. my command looked like this: `sudo chown -RH dyansberg: /opt/`
	3. Configure Remote Access step:
		1. your context.xml files (each webapp has one in their respective META-INF) should look exactly like this xml snippet (I think):
		   
		   <Context antiResourceLocking="false" privileged="true">
		   </Context>
			
	4.  You should now be able to access your tomcat server from your VM's public ip
		1. URL: `<public-ip>:8080/<webapp>`


# SailPoint to the Cloud!

## Set up a storage account on Azure

1. This can be accomplished by searching for and opening Storage accounts service
2. With the service open, select `Create` and follow the setup wizard
	1. BE SURE TO CHOOSE YOUR RESOURCE GROUP
	2. I don't remember any of the stuff I chose here, but I probably went with whatever was cheapest.

## Set up a File Share on that Storage account

1. After creating your Storage account, open it up. You can just select it from the Storage Account service page.
2. In the blade on the left, choose `File Shares`
3. select the + button and follow the wizard.
	1. Again I don't know how I set it up, but use your best judgement
4. Now you have a file share. yay!

## Upload SailPoint to the file share
1. navigate to your Storage account then navigate to your File Share
2. Select `upload` from the top
3. upload the SailPoint zip file that Jon gave us a few weeks ago

## Connect the File Share to your VM
1. Navigate to your File Share
2. Press `Connect` from the top.
3. from the blade on the right, select the Linux tab
4. Select `Show Script`
5. Copy the Script
6. Navigate to your VM and open it in a CLI
7. Paste the script and run it.
8. You should now have access to the SailPoint zip file on your VM  (woohoo!)

## Extract Extract SailPoint
1. From here on out, the steps are basically the same as we did in class, but you'll be doing it all through the command line.
2. I had to run `sudo apt install unzip` (or something like that) in order to unzip the zip file.
3. Now all we need to do is set up the database....


# Databussin'.

## Create your MySQL Database on Azure
1. in Azure, navigate to Azure Database for MySQL servers.
2. select the create button
3. We want to create a Flexible server (on the left)
4. In the wizard, on the networking tab, make sure you check the box to `Allow public access from any Azure service ...`
5. Add current client IP address to the firewall rules (its a button before the checkbox of the last step.)
7. You know the rest of the drill here, use your best judgement and make sure its in the correct resource group.


## Run the SailPoint database script

Remember that script that Jon had us run? It ended with `.mysql`... well find that script and change the extension to `.sql`.

1. Open whatever CLI tool you have that you can use the `az` command on.
2. Run the following command:
   `az mysql flexible-server execute -n <server-name> -u <username> -p "<password>" -d <database-name> --file-path "<file-path>"`
3. server-name can be gotten from your database's overview page in Azure
	1. I think I had to leave out the .com portion or something. I can't remember. try it with the whole name first then make adjustments.
4. username and password are the admin username and password you set up when you created the database.
5. file-path is the file-path to that SQL file with the creation script
6. and that's it!
7. Once it is complete (it might take a few minutes), you can confirm that the database was created by going to your database overview page and then selecting `Databases` from the blade on the left. identityiq and identityiqplugin should be in there.
8. docs for additional help : https://learn.microsoft.com/en-us/azure/mysql/flexible-server/connect-azure-cli

# Final Steps

Now with all that out of the way, we can connect our database to SailPoint by editing the iiq.properties file on the VM.

1. open the iiq.properties file on your VM. The path to iiq.properties should be relative to `<root-path>\webapps\identityiq\WEB-INF\classes`
	1. I used nano for this step. `sudo nano path\to\iiq.properties`
2. Now comment out the `dataSource.url`, `dataSource.driver` and `sessionFactory` lines.
	1. these are the same lines we updated in class, but they wont work for us here, so just comment them out.
3. the very next block of commented out code is titled MySQL/Aurora (SSL). Un comment the dataSource and sessionFactory lines of code in this block.
4. replace `localhost` with `<server-name-url>:3306`
	1. the server name can be found by navigating to your database in Azure. It is on the top right area of the overview page
5. now scroll down to he other area of code we edited before This section in the code is titled with a comment `MySQL (without SSL)`
	1. comment out the lines of code that begin with `pluginsDataSource`
6. Now the next block of code is title with `MySQL (SSL)`. Uncomment the `pluginsDataSource` lines of code in this section
7. replace `localhost` with `<server-name-url>:3306`
8. Be sure to add the context.xml to the META-INF folder as well if you still have issues getting into SailPoint.
9. And maybe try running `sudo systemctl stop tomcat` `sudo systemctl start tomcat`
10. Also be sure to add a port rule in your VM's network settings to allow all inbound traffic to the 8080 port if you want your teammates to be able to access it.