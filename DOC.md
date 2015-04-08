
0/ Introduction :
0.1/ Theme :
1/ La Plateforme vue D’avion: (Ludo)
2/ Demo Presentation: Carpet Showdown (David)
3/ On Passe du temps sur la Console d’admin  (David + Ludo)
4/ On Passe aussi du temps sur le Cloud SDK CLI (ludo)
5/ On Passe au Runtime Node.js Sur App engine
6/ Console: LOG VIEWER et Log Streaming to BIG  QUERY (David)
7/ SCALING APP ENGINE (Is it DONE?) ludo?
8/ Cloud DEBUGGER (ludo)
9/ KUBERNETES (David)
10/ OTHER: AWS, or HEROKU? (David)
11/ One More Thing: FIREBASE: (david)
12/ One More Thing: (ludo)
13/ Other Things: (David)
14/ Conclusion: 360 Guided Tour sure La Platform Cloud Google
15/ FREE TRIAL



0/ Introduction :

Presentation des Speakers David et Ludo

Actual Cloud Video   (sound to set up)

0.1/ Theme :

La plate forme qui s’ouvre: de app engine (privee, fermee) at GCE++
Application fil rouge déployée de plusieurs facons : Carpet showdown
La Brique de Base est une GCE VM. Et dedans, des Docker Containers.

1/ La Plateforme vue D’avion: (Ludo)

URL de depart: https://cloud.google.com/
Passer en revue les differentes pages rapidement
App Engine, Compute Engine, Datastore, Mysql, Kontainers, Big Query, Monitoring (StackDriver),...
Doc comparaison:
https://cloud.google.com/appengine/docs/managed-vms/

Creer une GCE VM sur devoxxcarpet via la console, la voir booter, et faire SSH et simple 
sudo apt-get update
sudo apt-get install apache2
Noter: toute Action dans le UI est dispo en COMMAND LINE ou POST REST


2/ Demo Presentation: Carpet Showdown (David)
What is it? David
Java App (Fluent server) et Node app utilisant la plupart des techno Google Cloud.
Sur github: https://github.com/CodeStory/devoxxcarpet
App Engine Specific: Dockerfile et app.yaml config file
Notion de Custom Runtime: Docker definition
Par Exemple: FLUENT HTTP CODESTORY Server Comme App engine Managed VMs!!!
https://github.com/CodeStory/fluent-http



Carpet Showdown - local - inMemory (David)
Carpet Showdown - local - Cloud DataStore (Ludo intro)
https://github.com/GoogleCloudPlatform/gcloud-java
ou pas…
Datastore: Ludo GCloud Datastore
Projects Github : Passer du temps à montrer les projets Par example
https://github.com/GoogleCloudPlatform/gcloud-node
http://googlecloudplatform.github.io/gcloud-node/#/

GRPC 
Open :
Open APis, all runtimes: tourne sur Mac, GCE, GAE, AWS
API Browser: https://console.developers.google.com/project/vmruntime-demo/apiui/api
GRPC: mouvement de fond Google a tout mettre en open source les techno internes  http://www.grpc.io/
Puis Docker (Transition to David)
Carpet Showdown - docker local - Cloud DataStore
Carpet Showdown - app engine run local - Cloud DataStore

So Far, tout est LOCAL sur la machine DEV… On Passe au Cloud reel: (David)

Carpet Showdown - docker machine - Cloud DataStore
Carpet Showdown - app engine deploy - Cloud DataStore


3/ On Passe du temps sur la Console d’admin  (David + Ludo)
https://console.developers.google.com/project/devoxxcarpet
On Fait une Demo avec la CAMERA Mobile de l’appli Console sur ANDROD

4/ On Passe aussi du temps sur le Cloud SDK CLI (ludo)

Terminal view of the HELP
Talk About Maven Plugin (Via NetBeans Code Completion and Doc Page?)


5/ On Passe au Runtime Node.js Sur App engine
Intro Node: Ludo
Demo Node: David

Carpet Showdown Node version - app engine run local - inMemory
Carpet Showdown Node version - app engine deploy- inMemory


6/ Console: LOG VIEWER et Log Streaming to BIG  QUERY (David)

Ajouts de logs dans la console (/var/log/app_engine/custom_logs), stream dans BigQuery et requete sur les votes et les gagnants
http://googlecloudplatform.blogspot.fr/2015/02/gain-business-and-operational-insight-into-your-applications-using-Google-Cloud-Logging.html

7/ SCALING APP ENGINE (Is it DONE?) ludo?

Scaling App Engine (ManualScaling = 2)
automatic_scaling:
  min_num_instances: 2
  max_num_instances: 20
  cool_down_period_sec: 60
  cpu_utilization:
    target_utilization: 0.5

See doc https://cloud.google.com/appengine/docs/managed-vms/config


8/ Cloud DEBUGGER (ludo)
Cloud debugger Java / Node
Git Registration, Code viewer, Commits View
Debug Java
Possibly Debug Node?


9/ KUBERNETES (David)

Pushing / Pulling from Container Registry
Déployer sur Kubernetes suivi d’un resizing

10/ OTHER: AWS, or HEROKU? (David)
Déployer sur Heroku avec une connexion au DataStore

11/ One More Thing: FIREBASE: (david)

Version backend-less avec Firebase



12/ One More Thing: (ludo) ?

CLOUD Launcher
Montrer Cloud Launcher 
en demandant a l’audience
Montrer le prix par mois
Deployer une stack
https://cloud.google.com/launcher/#/explore

http://googlecloudplatform.blogspot.com/2015/03/deploy-popular-software-packages-using-Cloud-Launcher.html?m=1
https://google.bitnami.com/


Delirium de Proppy:
https://gist.github.com/proppy/04f1270e015c0179b2d4

13/ Other Things: (David)

Live Migration Experience?
http://googlecloudplatform.blogspot.fr/2015/03/Google-Compute-Engine-uses-Live-Migration-technology-to-service-infrastructure-without-application-downtime.html?m=1
https://gigaom.com/2015/03/04/google-gets-chatty-about-live-migration-while-aws-stays-mum
Scalable deployment manager, Load balancer ????
http://googlecloudplatform.blogspot.com/2015/03/introducing-the-Scalable-and-Resilient-Web-Apps-Solution.html?m=1
Cloud tracing logs AppEngine et Compute Engine (david, based on experience?)
https://www.youtube.com/watch?v=NCFDqeo7AeY&feature=share	

14/ Conclusion: 360 Guided Tour sur La Platform Cloud Google
A Retenir:
API everywhere
App Engine V2 -> Managed VMs: Ouverture totale de la back box
Open Source (Kubernetes)
Stacked:
1 GCE
2/ Docker
3/ Kubernetes
4/ Then App Engine

15/ FREE TRIAL 
https://cloud.google.com/free-trial/



FIN FIN FIN FIN FIN FIN FIN FIN FIN FIN FIN FIN FIN 

Déployer un site web sur la plateforme cloud de Google est simple. On peut utiliser des VMs avec Compute Engine ou du PaaS avec App Engine.
Comment utiliser Docker pour deployer plusieurs services ?
Comment les faire communiquer ?
Mais comment monitorer nos services ?
Comment traiter / stocker / analyser les logs ?
Peut-on débugger une application en live ?
Je vous propose une université pour faire le tour du Cloud de Google et aller au delà des démos simplistes et vraiment découvrir comment déployer et administrer une application plus ambitieuse.
En bref, comment on passe la 5ème au lieu de rouler toujours en seconde.
------------------------------------------------------------------------------------





Idées de démo :
Deployer une base Nosql avec Cloud Launcher https://cloud.google.com/launcher/#/explore
Monter API browser: https://console.developers.google.com/project/vmruntime-demo/apiui/api




https://console.developers.google.com/project/vmruntime-demo/clouddev/trace?pid=vmruntime-demo&sort=startTimestamp&start=1428049550572&rev&v=default:fractal

Monitoring StackDriver????










------------------------------------------------



CLOUD Monitoring Agent (Stack Driver)
https://cloud.google.com/monitoring/agent/
You can install plugins on many many stack!

Scalable deployment manager, Load balancer
http://googlecloudplatform.blogspot.com/2015/03/introducing-the-Scalable-and-Resilient-Web-Apps-Solution.html?m=1

Live Migration:

