  ________  _____  ___        __       __   ___  _______       _______       __       ___      ___   _______  
 /"       )(\"   \|"  \      /""\     |/"| /  ")/"     "|     /" _   "|     /""\     |"  \    /"  | /"     "| 
(:   \___/ |.\\   \    |    /    \    (: |/   /(: ______)    (: ( \___)    /    \     \   \  //   |(: ______) 
 \___  \   |: \.   \\  |   /' /\  \   |    __/  \/    |       \/ \        /' /\  \    /\\  \/.    | \/    |   
  __/  \\  |.  \    \. |  //  __'  \  (// _  \  // ___)_      //  \ ___  //  __'  \  |: \.        | // ___)_  
 /" \   :) |    \    \ | /   /  \\  \ |: | \  \(:      "|    (:   _(  _|/   /  \\  \ |.  \    /:  |(:      "| 
(_______/   \___|\____\)(___/    \___)(__|  \__)\_______)     \_______)(___/    \___)|___|\__/|___| \_______) 
                                                                                                              

This is the Snake Game guide.

=============================================================================================================
1. LAUNCHING.
=============================================================================================================

Snake game launching instruction.
It's pretty easy to start the application.

Launch start.bat in the same folder with this text file.
If you want to know extra game configurations tools, you should either launch help.bat (in the same folder), 
or launch the snake application with the key "-h" or "--help", like this:

java -jar snake.jar --help

By using these parameters you can specify the game field size, the snake length, how much time will the snake
sleep and the frogs count.

=============================================================================================================
2. GAME PROCESS.
=============================================================================================================

In the bottom of the main game window there are three buttons: Start, Pause and Stop.
When you press the Start button, the game will be started.
Press Pause to pause the game. When the game is paused, you can resume it by pressing Start or you can stop
it by pressing Stop.

When the game is in progress, you can turn the snake to the left by clicking the left mouse button, and to
the right by clicking the right mouse button, respectively.

The snake will die if it eat itself or the blue frog. The game will be over then.
	

=============================================================================================================
3. TROUBLESHOOTING.
=============================================================================================================

If you have some troubles with starting an application, please check these options:

1) Check if you have Java Runtime Environment (https://www.java.com/verify). You should have at least 1.6
   version. Please, write down the installation path (for example, C:\Program Files\Java\jdk1.6.0_02), it
   can be necessary in the future.
2) If you don't have it, follow the instruction on the website above and install the latest version of Java
or at least 1.6 version.
3) Try to start the application again.
4) If it doesn't work, try to set the JAVA_HOME variable:

Set JAVA_HOME:
Make the right click on the My Computer folder and select "Properties".
On the "Advanced" tab, select "Environment Variables", and then edit "JAVA_HOME" to point to where the JRE
software is located, for example, "C:\Program Files\Java\jdk1.6.0_02"

5) Try to start the application again.
6) If it doesn't work, then you should try to change the PATH variable. 

a) Open start.bat with any plain text editor (notepad.exe, for example). Don't use MS Word for this purpose!
b) Delete the word "rem " (with space after it) in the second line. You will get something like this:
	
@echo off
setx PATH "%JAVA_HOME%\bin;%PATH%" 
java -jar snake.jar -n 50 -d 100 -l 50 -m 50 -f 2000
pause
	
c) Save this file.
d) Run it again.

7) If it doesn't work, please, contact us, we will help you!

=============================================================================================================
ENJOY THE GAME!
=============================================================================================================