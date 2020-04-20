Exercies with RabbitMq server - queueing tasks
 > Two Agencies\
 > Two Carriers\
 > One Admin

Agencies send tasks to do to carriers. Carriers perform two of three all
possible types of tasks (chosen on init). Carriers send ack after the job
is done.

Types of messages send by Agencies:
 > people\
 > equip\
 > space 

Admin role is to observe all messages that goes through Exchange,
and it can send message to group (agencies, carriers, both)
