# Quarantine.IO #

### What it is ###
It´s a free and open source mobile application that allows governments impose and enforce mandatory quarantine restrictions upon citizens, to avoid SARS-CoV-2 spreading amid current COVID-19 pandemic

### What it does ###
* Impose citizens a minimum time interval to leave their residences to acquire comestibles and items in shops (e.g.: once every 7 days). They will be able to check their status and next authorized date
* Merchants will be able to query a citizen´s status, and evaluate if he or she has authorization to leave their residences and make purchases that day
* Law enforcement officials such as security forces or deployed armed forces will be able to query a citizen´s status, to evaluate if he or she has authorization to leave their residences and circulate in public spaces that day

### How it works ###
Citizens must to register by sending their data and a picture of their natonal identification document. User data will be checked by image recognition A.I. that will verify if it matches with data found in the uploaded picture. After being verified, the individual will be able to generate a QR code. Authorities or merchants will be able to scan the citizen´s QR code with their camera and evaluate if that subject is allowed or not to leave their residence.

### How I built it ###
It was coded in Java using Android Studio IDE. Cloud infrastructure relies on Google Cloud Computing, Google Firebase Realtime Database and Google Artificial Intelligence ML Kit.

### What's next for Qarantine.IO ###
In first place it lacks a feature that allows grantinig permanent authorization to essential personnel (health professionals, security and armed forces and government officials). Also unauthorize citizens that have recently arrived from other countries to leave their residences for 14 days. Finally, record citizens violating enforced mandatory quarantine by leaving their residence when their status is unauthorized. Making easier further legal punishments
