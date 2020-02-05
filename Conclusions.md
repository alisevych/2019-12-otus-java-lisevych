# Анализ эффективности Java Garbage Collector
Сравнение производилось на основе следующих показателей:
1. среднее время на сборки в минуту (сек/мин)<br/>
в логе видно, как растет этот показатель (увеличивается время на сборки)<br/>
вплоть до java.lang.OutOfMemoryError: Java heap space
2. продолжительность работы программы GCDemo до OutOfMemoryError (мин)
## Serial GC
1. 1,69 sec/min
2. 5,58 min
## Parallel GC
1. 1,30 sec/min
2. 4,32 min
## CMS GC (Concurrent)
1. 13,03 sec/min
2. 6,04 min
## G1 GC
1. 0,72 sec/min
2. 5.39 min
## Выводы
Дольше всего программа остается частично живой на ConcMarkSweep GC.<br/>
Но минимальное время даунтайма по время сборок - на G1 GC.<br/>
Поскольку разница времени даунтайма ~ в 20 раз, больше, чем на порядок, а <br/>
разница времени работы программы ~ на 10%, то есть продолжительность времени работы программы сходная,
**рекомендуется использовать G1 GC для данной системы и данной программы.**<br/>
## Конфигурация системы, на которой проводились измерения ##
Operating System: Windows 10 10.0<br/>
Architecture: amd64<br/>
Number of processors: 4<br/>
Committed virtual memory: 631 760 kbytes<br/>
Total physical memory: 16 662 388 kbytes<br/>
Free physical memory:  4 635 388 kbytes<br/>
Total swap space: 22 978 660 kbytes<br/>
Free swap space:  3 372 792 kbytes<br/>
Maximum heap size: 524 288 kbytes<br/>
