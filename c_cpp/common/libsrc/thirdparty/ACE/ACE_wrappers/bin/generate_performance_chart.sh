#!/bin/sh
#
# generate_performance_chart.sh,v 4.5 2006/01/26 13:21:00 jwillemsen Exp
#

gnuplot <<_EOF_ >/dev/null 2>&1
    set xdata time
    set timefmt '%Y/%m/%d-%H:%M'
    set xlabel 'Date (YYYYMMDD)'
    set ylabel 'Throughput (Requests/Second)'
    set terminal png small size 800,600 color
    set yrange [4000:25000]
    set output "$2"
    plot '$1' using 1:2 title '$3' w l
    exit
_EOF_
