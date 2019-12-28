

$fn = 72;

Thickness=1.9425;
Radius = 25;

difference () {
    union () {
        cylinder(r=Radius, h=Thickness, center=true);
        rotate_extrude(angle=360,convexity=10)
            translate([Radius, 0, 0])
                circle(r=Thickness/2);
    }
    
    union () {
        TappedHole(3.25,Thickness);
        translate([8,0,0])
            TappedHole(2,Thickness);
        translate([-8,0,0])
            TappedHole(2,Thickness);
        translate([0,8,0])
            TappedHole(2,Thickness);
        translate([0,-8,0])
            TappedHole(2,Thickness);
        
    }
}


module TappedHole(r, h) {
    cylinder(r=r, h = h + 2, center=true);
    translate([0,0,-(h+1)/2])
        cylinder(r1 = r + 1, r2 = r, h = 1);
}