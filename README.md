#Triangle Genome
Version 1.0

##Authors
* Troy Squillaci <zivia@unm.edu>
* Jake Nichol <jjaken@unm.edu>
* Jayson Grace <jaysong@unm.edu>

### How do I get set up? ###

* Clone into the repository:

```
#!bash
git clone https://bitbucket.org/teampizza520minus100/trianglegenome

```

### Basic program usage instructions ###
1. Import the project into Eclipse
2. Run Main.java located in the trigen.application package
3. Select the image you would like to work with on the left-hand side of the Application
4. Specify the number of Tribes you want using the **Tribe Count** slider
5. Click the **Set Tribes** button
6. Click **Next** button for one generation or **Start** button to run endlessly

### Functionality of the program Explained ###
* The **Pause** button will pause all running threads
* The **Write Genome File** button will create an xml file with the active genome (the one currently displayed)
* The **Read Genome File** button will read an xml file previously created using the **Write Genome File** button
* The **Selected Triangle** slider allows you to view a specific Triangle in the currently displayed Genome

### Documentation ###
1. Formal Javadoc API documentation can be found in the api-doc folder.
2. To access the web interface table of contents for javadoc, open index.html

### Additional Folders ###
* testImages - contains images for fitness testing

### Footnotes ###
* CPU usage will rarely reach higher than 85%.  This is because our tribe controller thread is most often waiting for other threads.
* We are missing some components of our statistical analysis, such as Tribe and Total Population Delta Fitness per Minute and Tribe and Total Population Diversity.
* After running the program with a specific image, you need to shut down the program in order to use another image.