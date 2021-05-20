/*
   public Circle createUnitCircle(Point p, Point q, double radius) {

   Point midpoint = p.midPoint(q);

//Translate midpoint by some angle of distance d.
//Note: Refer to diagram in index.html to get what I mean

//Getting angle
double angle= Math.PI * 0.5 - p.angleTo(q);

//Getting distance d(Just Pythagoras theorem
double distance = Math.sqrt( square(radius) - square( p.distanceTo(midpoint) ) );

//Translation occurs
return Circle.createCircle( p.moveTo(angle, distance) , radius );  
}
 */

public Circle createUnitCircle(Point p, Point q) {

    //We cant find a circle that cuts both points if  p.distanceTo(q)>2*radius
    if(p.distanceTo(q)>2*1) {
        return Circle.createCircle(Point.createPoint(0, 0), Double.NaN);
    }

    //If same points, just make that the centre lor
    if(p.distanceTo(q)==0) {
        return Circle.createCircle( p , 1 );
    }

    Point midpoint = p.midPoint(q);

    //Translate midpoint by some angle of distance d.
    //Note: Refer to diagram in index.html to get what I mean

    //Getting angle
    double angle= Math.PI * 0.5 - p.angleTo(midpoint) ;

    //Getting distance d(Just Pythagoras theorem)
    double distance = Math.sqrt( square(1) - square( p.distanceTo(midpoint) ) );

    //Translation occurs
    return Circle.createCircle( midpoint.moveTo(angle, distance) , 1 );  
    Point midpoint = p.midPoint(q);
    double distanceToQ = p.distanceTo(q);
        double angle = p.angleTo(q) - Math.PI / 2;
        double d = Math.sqrt(1 - distanceToQ / 2 * distanceToQ / 2);
        //move midpoint at angle by d
        return Circle.createCircle(midpoint.moveTo(angle, d), 1);

}

private double square(double num)
{
    return num * num;
}

public int findMaxDiscCoverage(Point[] points) {

    
    int max=1; //cfm max points should be 1
    int curr1=0, curr2=0;    
    int base_ofPair, forming_pair;//my counters

    for (base_ofPair = 0; base_ofPair<=points.length-1; base_ofPair++) //iterates through every point to establish the base of the pair of points
    {
        for (forming_pair = 0; forming_pair<=points.length-1; forming_pair++) //Forms a pair with the base of pair
        {
            //reset
            curr1=0;
            curr2=0;

            //A disc that covers the maximum number of points must pass through at least two points. These 2 points are the pair.
            //Atmost 2 circles can be created from 2 points
            Circle circle1 = createUnitCircle(points[base_ofPair], points[forming_pair] );
            Circle circle2 = createUnitCircle(points[forming_pair], points[base_ofPair] );

            if( Double.isNaN(circle1.getRadius()) )
                continue;

            // count the number of points inside the circle
            for (int k = 0; k <=points.length-1; k++) 
            {

                if (circle1.contains(points[k])) 
                {	
                    curr1++;

                }

                if (circle2.contains(points[k])) 
                {	
                    curr2++;

                }
                
            }

            max=Math.max( Math.max(curr2,curr1) ,max);
        }
    }

    return max;

} 
