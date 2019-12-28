holeFacets = 144;


mountWidth = 95;
mountThickness = 2;
mountDepth = 32+mountThickness;
mountHeight = 32+mountThickness;


motorOffset=0;

motorNarrowRadius = 36.0 / 2;

motorWideDepth = 26;
motorInnerRadius = 37.25/2;
motorWallThickness = 3;
motorOuterRadius = motorInnerRadius+motorWallThickness;

echo(motorOuterRadius);
mountRadius=4;

cornerRadius = 2.5;

bandWidth= 13;
bandThickness = 1;
bandOffset = 2;

echo(bandWidth);

holeRadius = 2;
holeDepth = 100;
holeOffset = 8;

gap=3;
chamfer = 0.5;

lowerBaseWidth = 36;

baseWidth = 32;
twoBands = false;
twoRadii = false;

Mount();

/*
difference() {
    cylinder(r=11,h=mountThickness,$fn=holeFacets);
    
    translate([0,0,mountThickness/2]) {
    Holes();
        
    cylinder(r=5,h=2,$fn=holeFacets);
    cylinder(r=3.5,h=holeDepth,center=true,$fn=holeFacets);}
}
*/

module Mount() {
    p1 = [lowerBaseWidth, -mountRadius];
    p2 = [0,-(motorOuterRadius+motorOffset)];
    p12 = RLTangent(p1, p2, mountRadius, motorOuterRadius, -1);
    p21 = RLTangent(p2, p1, motorOuterRadius,  mountRadius, -1);
    p3 = [-baseWidth, -(mountRadius + 32 - mountThickness)];
    p23 = RLTangent(p2, p3, motorOuterRadius, mountRadius, 1);
    p32 = RLTangent(p3, p2, mountRadius, motorOuterRadius, 1);
    
    echo(p12);
    echo(p21);
    difference() {
        union() {
            
            linear_extrude(height = mountDepth)
                polygon([[lowerBaseWidth,0], p1, p12, p21, p23, p32,
                         [-baseWidth, -32 + mountThickness],
                         [-mountWidth/2,-32 + mountThickness], [-mountWidth/2,0]]);

            translate([-mountWidth/2,0,0])
                cube([mountWidth, mountHeight, mountThickness]);
            
            translate([-mountWidth/2,0,0])
               cube([mountWidth,mountThickness,mountDepth]);
            
            translate([-mountWidth/2,0,0])
                cube([mountWidth,cornerRadius+mountThickness,cornerRadius+mountThickness]);
            
            translate([0,-(motorOuterRadius+motorOffset),0])
                cylinder(r=motorOuterRadius,h=mountDepth,$fn=holeFacets);
            
        }
        
        translate([32,(mountHeight-16),-1])
            CornerCut(2*(mountWidth/2-32),32,mountThickness+2);
        
        /*translate([-32,-50,(mountDepth-8)])
            rotate([90,0,180])
                CornerCut(2*(mountWidth/2-32),16,100);*/
        translate([-(mountWidth/2 - 1),-50,(mountDepth-1)])
            rotate([90,0,180])
                CornerCut(2,2,100);
        translate([-(mountWidth/2 - 1),-50,1])
            rotate([-90,90,0])
                CornerCut(2,2,100);
        
        translate([(mountWidth/2 - 1),-50,1])
            rotate([-90,0,0])
                CornerCut(2,2,100);
        
        scale([-1,1,1]) {
            translate([32,(mountHeight-16),-1])
                CornerCut(2*(mountWidth/2-32),32,mountThickness+2);
            translate([-32,-1,(mountDepth-16)])
                rotate([90,0,180])
                    CornerCut(2*(mountWidth/2-32),32,mountThickness+2);
        }
        
        translate([0,cornerRadius+mountThickness,cornerRadius+mountThickness])
            rotate([0,90,0])
                cylinder(r=cornerRadius,h=mountWidth+2,center=true,$fn=holeFacets);
        
        translate([p1.x, p1.y, -1])
                cylinder(r=mountRadius,h=mountDepth+2,$fn=holeFacets);
        translate([p3.x, p3.y, -1])
                cylinder(r=mountRadius,h=mountDepth+2,$fn=holeFacets);
            
        if (twoRadii)
        {
            color("red") translate([0,-(motorOuterRadius+motorOffset),-2])
                cylinder(r=motorNarrowRadius,h=mountWidth+4,$fn=holeFacets);
            translate([0,-(motorOuterRadius+motorOffset),-1])
                cylinder(r=motorInnerRadius,h=motorWideDepth+1,$fn=holeFacets);
            translate([0,-(motorOuterRadius+motorOffset),motorWideDepth])
                cylinder(r1=motorInnerRadius,r2=motorNarrowRadius,
                         h=motorInnerRadius-motorNarrowRadius,$fn=holeFacets);
        }
        else {
            translate([0,-(motorOuterRadius+motorOffset),-1])
                cylinder(r=motorInnerRadius,h=mountDepth+2,$fn=holeFacets);
        }
     
        
        translate([0,-(motorOuterRadius+motorOffset),-chamfer])
            cylinder(r1=motorInnerRadius+chamfer*2, r2 = motorInnerRadius,h=chamfer*2,
                     $fn=holeFacets);   
        if (twoBands) {
            translate([0,-(motorOuterRadius+motorOffset),
                       (bandWidth+bandThickness)/2+bandOffset])
                Band();
            translate([0,-(motorOuterRadius+motorOffset),
                      mountDepth - ((bandWidth+bandThickness)/2+bandOffset)])
                Band();
        } else {
            translate([0,-(motorOuterRadius+motorOffset),motorWideDepth/2])
                Band();
        }
        
      
        translate([-32,(16+mountThickness),mountThickness/2]) {
            Holes();        

        }
        
       //   translate([0,(16+mountThickness),mountThickness/2]) {  
       //         cylinder(r=5,h=2,$fn=holeFacets);
       //     cylinder(r=3.5,h=holeDepth,center=true,$fn=holeFacets);
       //  }
        
        translate([32,(16+mountThickness),mountThickness/2])
            Holes();

        translate([-32,mountThickness/2,(16+mountThickness)])
            rotate([90,0,0])
                Holes(left=false, top=false, bottom = false); 
                
        translate([32,mountThickness/2,(16+mountThickness)])
            rotate([90,0,0])
                Holes(right=false, top=false, bottom = false); 

        translate([-gap/2,-(2*motorOuterRadius+motorOffset+1),-1])
               cube([gap,motorWallThickness+2,mountDepth+12]);
        
    }
}

function RLTangent(p1, p2, r1, r2, s) =
     let(dx = (p2[0] - p1[0]))
     let(dy = (p2[1] - p1[1]))
     let(distance2 = dx*dx + dy*dy)
     let(distance = sqrt(distance2))
     let(angleA = atan2(dy, dx))
     let(angleB = acos((r1+r2)/distance))
        [p1[0] + cos(angleA + angleB * s) * r1,
         p1[1] + sin(angleA + angleB * s) * r1];



module CornerCut(width, height, thickness) {
    difference() {
        cube([1+width/2,1+height/2,thickness]);
    
        scale([1,height/width,1])
            translate([0,0,-1])
                cylinder(r=width/2,h=thickness+2,$fn=holeFacets);
    }
}

module Band()
{
            rotate_extrude(convexity=10, $fn=holeFacets)
            translate([motorOuterRadius+bandThickness/2+0.005,0,0]) {
                translate([0,bandWidth/2])
                    circle(r=bandThickness/2, $fn=holeFacets);
                translate([0,-bandWidth/2])
                    circle(r=bandThickness/2, $fn=holeFacets);
                polygon([//[0,bandWidth/2+1],
                         [bandThickness/2,bandWidth/2],
                         [bandThickness/2,-bandWidth/2],
                        // [0,-bandWidth/2-1],
                         [-bandThickness/2,-bandWidth/2],
                         [-bandThickness/2,bandWidth/2]]);
            }
}

module Holes(top=true,left=true,right=true,bottom=true) {
    if (left) {
        translate([holeOffset,0,0])
            cylinder(r=holeRadius,h=holeDepth,
                     center=true, $fn=holeFacets); }
    if (right) {
        translate([-holeOffset,0,0])
            cylinder(r=holeRadius,h=holeDepth,
                     center=true, $fn=holeFacets); }
    if (bottom) {            
        translate([0,holeOffset,0])
            cylinder(r=holeRadius,h=holeDepth,
                     center=true, $fn=holeFacets); }
    if (top) {
        translate([0,-holeOffset,0])
            cylinder(r=holeRadius,h=holeDepth,
                     center=true, $fn=holeFacets); }  
}
