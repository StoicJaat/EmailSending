# EmailSending
A Java Spring Boot service for sending personalized emails with PDF attachments using SendGrid's email API.

✅ Features
Read email list from Excel file
Send resume to each email using SendGrid
Uses SendGrid Dynamic Email Template
Tracks email sending status

📁 Excel Format
Make sure your email_list.xlsx file looks like this:

Serial	Source	Email
112	Linkedin	recruitment.agrawalpower@gmail.com	

🛠️ Prerequisites
Java 
Maven
Spring Boot
Internet connection
SendGrid account (Free tier works)

🔧 SendGrid Setup
Create a free account – https://sendgrid.com
You get 100 emails/day in Free Tier
Go to Email API → Dynamic Templates
Create a dynamic template
Copy the Template ID
Go to Settings → API Keys
Create API Key

Save the key in code

⚙️ How to Use This Project
Clone the repo
bash
git clone (repo path)
cd sendgrid-springboot-mailer
Add your config in application.properties

sendgrid.api.key=YOUR_SENDGRID_API_KEY
sendgrid.template.id=YOUR_DYNAMIC_TEMPLATE_ID
Place your Excel file and resume/document in the proper folders

Excel file: /src/main/resources/email_list.xlsx
Resume/document: /src/main/resources/resume.pdf (or any file)
Run the project

mvn spring-boot:run

📌 Notes
Make sure your email template uses variables like {{source}}, {{email}}, same in the excel with same order and from starting dont give space in after the field names.



