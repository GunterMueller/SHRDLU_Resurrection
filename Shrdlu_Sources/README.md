 It is essentail that Java3D is installed.

For Ubuntu based  systems, like Mint, do the following:

3. Install Java 3D
3.1 Check Java 3D

Download test file via this link.

Try to run test3d.jar in terminal.
> java -jar test3d.jar

When you get the following message, you need to install Java 3D.
Exception in thread "main" java.lang.NoClassDefFoundError: javax/media/j3d/Canvas3D
at java.lang.Class.getDeclaredMethods0(Native Method)
......

When you see a form with 3D “Niche Analyst” in Fig.1 , congratulations! You make Java 3D run on your computer.

Figure 1. Java 3D testing application.

3.2 Install Java 3D

Download Java 3D via this link.

Go to the JRE folder, and execute the following command.

Note:

For a common installation, JRE is located at /usr/lib/jvm/default-java/jre.
> cd /usr/lib/jvm/default-java/jre
> sudo sh /path-to-download-files/java3d-1_5_1-linux-amd64.bin

Then, go back to 3.1 to check the Java 3D.

( from http://nichea.sourceforge.net/installation_nix.html )
