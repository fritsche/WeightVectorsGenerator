#!/bin/bash

# two layer weight vector generation

case $1 in
	"2" )
	m=2
	h1=96
	h2=0
	t=0.5
	;;
	"3" )
	m=3
	h1=12
	h2=0
	t=0.5
	;;
	"5" )
	m=5
	h1=6
	h2=0
	t=0.5
	;;
	"8" )
	m=8
	h1=3
	h2=2
	t=0.5
	;;
	"10" )
	m=10
	h1=3
	h2=2
	t=0.5
	;;
	"15" )
	m=15
	h1=2
	h2=1
	t=0.5
	;;
	"20" )
	m=20
	h1=2
	h2=1
	t=0.5
	;;
esac

javac WeightVectorsGenerator.java

if [ "$?" = "0" ]; then
	#1 layer generation
	java WeightVectorsGenerator -h $h1 -m $m -t 1.0 > first_layer.dat

	#2 layer generation
	java WeightVectorsGenerator -h $h2 -m $m -t $t > second_layer.dat

	# get the number of generated vectors for each layer
	a=$(wc -l < first_layer.dat)
	b=$(wc -l < second_layer.dat)

	rm -f $m"m.dat"
	# get the extreme points from first layer
	for (( i = 1; i <= $m; i++ )); do
		sort -k$i first_layer.dat > aux.dat
		cat aux.dat | tail -n 1 >> $m"m.dat"
		head -n -1 aux.dat > first_layer.dat
	done

	cat first_layer.dat >> $m"m.dat"
	cat second_layer.dat >> $m"m.dat"

	echo $(($a+$b)) > $m.dat
	cat $m"m.dat" >> $m.dat

else
	echo "COMPILATION ERROR!"
fi

echo "done."
