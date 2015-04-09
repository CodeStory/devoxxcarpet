## Introduction: ##

Presentation des Speakers David et Ludo

Actual Cloud Video   (sound to set up)  https://www.youtube.com/watch?v=Cp10_PygJ4o  

## Theme ##

La plate forme qui s’ouvre: de app engine (privee, fermee) at GCE++  
Application fil rouge déployée de plusieurs facons : Carpet showdown  
La Brique de Base est une GCE VM. Et dedans, des Docker Containers.  

## La Plateforme vue d’avion

URL de depart: https://cloud.google.com/  
Passer en revue les differentes pages rapidement  
App Engine, Compute Engine, Datastore, Mysql, Kontainers, Big Query, Monitoring (StackDriver),...  
Doc comparaison:  
https://cloud.google.com/appengine/docs/managed-vms/ 
Creer une GCE VM sur devoxxcarpet via la console, la voir booter, et faire SSH et simple  

sudo apt-get update  
sudo apt-get install apache2

Noter: toute Action dans le UI est dispo en COMMAND LINE ou POST REST


## Demo Presentation: Carpet Showdown
What is it?
Java App (Fluent server) et Node app utilisant la plupart des techno Google Cloud.  
Sur github: https://github.com/CodeStory/devoxxcarpet  
App Engine Specific: Dockerfile et app.yaml config file  
Notion de Custom Runtime: Docker definition  
Par Exemple: FLUENT HTTP CODESTORY Server Comme App engine Managed VMs!!!  
https://github.com/CodeStory/fluent-http  

Carpet Showdown - local - inMemory  
Carpet Showdown - local - Cloud DataStore  
https://github.com/GoogleCloudPlatform/gcloud-java  
ou pas…  
Datastore: gloud Datastore   
Projects Github : Passer du temps à montrer les projets Par example  
https://github.com/GoogleCloudPlatform/gcloud-node  
http://googlecloudplatform.github.io/gcloud-node  

### GRPC 

Open APis, all runtimes: tourne sur Mac, GCE, GAE, AWS  
API Browser: https://console.developers.google.com/project/vmruntime-demo/apiui/api  
GRPC: mouvement de fond Google a tout mettre en open source les techno internes  http://www.grpc.io/  
Puis Docker  
Carpet Showdown - docker local - Cloud DataStore  
Carpet Showdown - app engine run local - Cloud DataStore  

So Far, tout est LOCAL sur la machine DEV… On Passe au Cloud reel:  

Carpet Showdown - docker machine - Cloud DataStore  
Carpet Showdown - app engine deploy - Cloud DataStore  


## On Passe du temps sur la Console d’admin
https://console.developers.google.com/project/devoxxcarpet  
On Fait une Demo avec la CAMERA Mobile de l’appli Console sur ANDROID  

##  On Passe aussi du temps sur le Cloud SDK CLI

Terminal view of the gcloud --help  
Talk About Maven Plugin (Via NetBeans or IntelliJ Code Completion and Doc Page)  


## On Passe au Runtime Node.js Sur App engine


Carpet Showdown Node version - app engine run local - inMemory  
Carpet Showdown Node version - app engine deploy- inMemory  


## Console: LOG VIEWER et Log Streaming to BIG QUERY

Ajouts de logs dans la console (/var/log/app_engine/custom_logs), stream dans BigQuery et requete sur les votes et les gagnants  
http://googlecloudplatform.blogspot.fr/2015/02/gain-business-and-operational-insight-into-your-applications-using-Google-Cloud-Logging.html  

## SCALING APP ENGINE

Scaling App Engine (ManualScaling = 2)  
automatic_scaling:  
  min_num_instances: 2  
  max_num_instances: 20  
  cool_down_period_sec: 60  
  cpu_utilization:  
    target_utilization: 0.5  
  
See doc https://cloud.google.com/appengine/docs/managed-vms/config  


## Cloud DEBUGGER
Cloud debugger Java / Node  
Git Registration, Code viewer, Commits View  
Debug Java  
Possibly Debug Node as well!!!!  


## KUBERNETES

Pushing / Pulling from Container Registry  
Déployer sur Kubernetes suivi d’un resizing  

## OTHER: AWS, or HEROKU

Déployer sur Heroku avec une connexion au DataStore  

## One More Thing: FIREBASE

Version backend-less avec Firebase  



## One More Thing

CLOUD Launcher  
Montrer Cloud Launcher   
en demandant a l’audience
Montrer le prix par mois
Deployer une stack
https://cloud.google.com/launcher/#/explore  

http://googlecloudplatform.blogspot.com/2015/03/deploy-popular-software-packages-using-Cloud-Launcher.html?m=1
https://google.bitnami.com/


Proppy:
https://gist.github.com/proppy/04f1270e015c0179b2d4

## Other Things

Live Migration Experience:  
http://googlecloudplatform.blogspot.fr/2015/03/Google-Compute-Engine-uses-Live-Migration-technology-to-service-infrastructure-without-application-downtime.html?m=1  
https://gigaom.com/2015/03/04/google-gets-chatty-about-live-migration-while-aws-stays-mum
Scalable deployment manager, Load balancer   
http://googlecloudplatform.blogspot.com/2015/03/introducing-the-Scalable-and-Resilient-Web-Apps-Solution.html?m=1  
Cloud tracing logs AppEngine et Compute Engine (david, based on experience?)  
https://www.youtube.com/watch?v=NCFDqeo7AeY&feature=share  	

## Conclusion: 360 Guided Tour sur La Platform Cloud Google
A Retenir:  
API everywhere  
App Engine V2 -> Managed VMs: Ouverture totale de la back box  
Open Source (Kubernetes)  
Stacked:  
1 GCE  
2/ Docker  
3/ Kubernetes  
4/ Then App Engine  

## FREE TRIAL 

https://cloud.google.com/free-trial/  

