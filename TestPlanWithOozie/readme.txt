Login to linux using hadoop

$HADOOP_HOME/sbin/start-all.sh
$OOZIE_HOME/bin/oozie-start.sh
#port 10020
mr-jobhistory-daemon.sh  --config /opt/hadoop/etc/hadoop/  start  historyserver



#copy jar, workflow.xml, and job.properties file under /opt/hadoop/testplan

hadoop fs -put testplan testplan

cd /opt/hadoop/testplan

hadoop fs -put -f /opt/hadoop/testplan/lib /testplan

hadoop fs -put -f /opt/hadoop/testplan/coordinator.xml /testplan

oozie job -oozie http://localhost:11000/oozie -config job.properties -run

oozie job -oozie http://localhost:11000/oozie -config job-coord.properties -run

oozie job -oozie http://localhost:11000/oozie -info 0000000-180515212707444-oozie-hado-W