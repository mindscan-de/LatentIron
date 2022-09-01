# LatentIron

The idea for this tool is to implement an image/video dataset labeling tool with the possibility to build
and export datasets using these labels. As in my previous implementations, this will be implemeted without
a need for a database. All data will be again kept in a file structure, which basically acts as a database.

Some of the code will be take-overs from my source code search engine combined with with some insigths from 
multiple other projects, where I accessed FastAPI web services from a GUI. Such web services can be used to
use other pretrained models generate and then to store the extracted labels for images and make their content
searchable.

Probably LatentIron will be a precursor of another tool which i had in mind to model Real-World-Data with a
DAG using some triple grammars. But I will keep keep this idea for later.

This tool should mainly serve my own needs. Therefore please refrain from any suggestions or bug reports.

## Labeldata - Indexer

* Create a crawler for the files
* Calculate the location hash
* For each location hash create a metadata file
* Create an inverse trigram index over these metadatafiles and use hashcode for trigrams instead of dealing with issues with utf-8 encoded chars
  * trigramindex contains a list of location hashes, where the meta data contains the particular trigram
* this is a slightly modified version of my current searchengine

* In this case the index must be designed to be updateable rather than static

## Labeldata - Searchengine

* This is only a metadata search instead of a "content search"
* Maybe both are interesting, but metadata is more important

## Labeldata - Exporter

* create a static set
* create a dynamic set (e.g. in case of that images are re-labeled)
* export labeled files into some kind of file structure
* export labeled data into some easy to read files e.g. csv, jsonl, or something similar

## Labeling-GUI

* Create a SWT application
* Use mechanisms from the log analysis tool, like the command and event system and the plugin system
* Implement simple search in GUI
  * Make search results drag and dropable, such that the file location can be dropped into an image viewer able of opening dropped files.
* Implement simple labeling for Images