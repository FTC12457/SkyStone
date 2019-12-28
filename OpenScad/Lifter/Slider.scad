Length = 28;

InnerWidth = 6;
Padding = 2;
TrackWidth = 2.5;
OuterTrackWidth = 2.75;
OuterWidth = 12 - 3 /*nut*/ - Padding - TrackWidth;
TopWidth = 8;
TrackDepth = 24;
OuterTrackDepth = 24.25;
HoleOffset = TrackDepth-16;
Chamfer = 1;


$fn = 72;

p1 = [InnerWidth/2 + OuterTrackWidth + OuterWidth/2, 0];
p2 = [p1.x, OuterTrackDepth + OuterTrackWidth - TopWidth + OuterWidth  + 2];
p3 = [-(InnerWidth/2 + TrackWidth), TrackDepth + TrackWidth];


p12 = RRTangent(p1, p2, OuterWidth/2, TopWidth, -1);
p21 = RRTangent(p2, p1, TopWidth, OuterWidth/2, 1);
p23 = RRTangent(p2, p3, TopWidth, OuterWidth, -1);
p32 = RRTangent(p3, p2, OuterWidth, TopWidth, 1);

difference() {
    union () {
        linear_extrude(height = Length, center=true)
                polygon([[InnerWidth/2 + TrackWidth, 0],
                         p12,
                         p21,
                         p23,
                         p32,
                         [-(InnerWidth/2 + TrackWidth + OuterWidth),
                          TrackDepth + TrackWidth],
                         [-(InnerWidth/2 + TrackWidth + OuterWidth), 0],
                         [-(InnerWidth/2 + TrackWidth), 0],
                         [-(InnerWidth/2), 0]]);
            
        translate([p2.x, p2.y, 0])
            cylinder(r = TopWidth, h=Length, center=true);
        translate([p3.x, p3.y,0])
            cylinder(r = OuterWidth, h=Length, center=true);
        translate([InnerWidth/2 + OuterTrackWidth + OuterWidth/2, 0,0])
            cylinder(r = OuterWidth/2, h=Length, center=true);
        translate([-(InnerWidth/2 + TrackWidth + OuterWidth/2), 0,0])
            cylinder(r = OuterWidth/2, h=Length, center=true);
            
        cylinder(r = InnerWidth/2, h=Length, center=true);
    }
                
    
     union() {
            translate([InnerWidth/2+OuterTrackWidth/2,OuterTrackDepth,0])
            rotate([90,0,0])
            linear_extrude(height=OuterTrackDepth+22)
                polygon([[OuterTrackWidth/2 + Chamfer*2, -(Length/2 + Chamfer)],
                         [OuterTrackWidth/2, -(Length/2 - Chamfer)],
                         [OuterTrackWidth/2,  (Length/2 - Chamfer)],
                         [OuterTrackWidth/2 + Chamfer*2,  (Length/2 + Chamfer)],
                         [-(OuterTrackWidth/2 + Chamfer*2),  (Length/2 + Chamfer)],
                         [-(OuterTrackWidth/2),  (Length/2 - Chamfer)],
                         [-(OuterTrackWidth/2),  -(Length/2 - Chamfer)],
                         [-(OuterTrackWidth/2 + Chamfer*2),  -(Length/2 + Chamfer)]]);
         
            translate([-(InnerWidth/2+TrackWidth/2),TrackDepth,0])
            rotate([90,0,0])
            linear_extrude(height=TrackDepth+22)
                polygon([[TrackWidth/2 + Chamfer*2, -(Length/2 + Chamfer)],
                         [TrackWidth/2, -(Length/2 - Chamfer)],
                         [TrackWidth/2,  (Length/2 - Chamfer)],
                         [TrackWidth/2 + Chamfer*2,  (Length/2 + Chamfer)],
                         [-(TrackWidth/2 + Chamfer*2),  (Length/2 + Chamfer)],
                         [-(TrackWidth/2),  (Length/2 - Chamfer)],
                         [-(TrackWidth/2),  -(Length/2 - Chamfer)],
                         [-(TrackWidth/2 + Chamfer*2),  -(Length/2 + Chamfer)]]);
         
        translate([InnerWidth/2 + OuterTrackWidth/2, OuterTrackDepth,0])
            TappedHole(OuterTrackWidth);
        translate([-(InnerWidth/2 + TrackWidth/2), TrackDepth,0])
            TappedHole(TrackWidth);
         
         translate([Padding-InnerWidth/2,HoleOffset,8])
            Hole();
         translate([Padding-InnerWidth/2,HoleOffset,-8])
            Hole();
         translate([Padding-InnerWidth/2,HoleOffset+8,0])
            Hole();
     }   
}

module TappedHole(w) {
    cylinder(r = w/2, h=Length+2, center=true);
    translate([0,0,-(Length/2 + Chamfer)])
        cylinder(r1 = w/2 + Chamfer*2, r2 = w/2, h = 2*Chamfer);
    translate([0,0,Length/2 - Chamfer])
        cylinder(r1 = w/2, r2 = w/2 + Chamfer*2, h = 2*Chamfer);
}


module Hole() {
    rotate([0,90,0]) {
        translate([0,0,-50])
            cylinder(r = 2, h=51);
        cylinder(r = 3, h=51);
     }
}

function RRTangent(p1, p2, r1, r2, s) =
     let(dx = (p2[0] - p1[0]))
     let(dy = (p2[1] - p1[1]))
     let(distance2 = dx*dx + dy*dy)
     let(distance = sqrt(distance2))
     let(nx = dx / distance)
     let(ny = dy / distance)
     let(cosA = (r1-r2)/distance)
     let(sinA = sqrt(1-cosA*cosA))
        [p1[0] + (cosA * r1) * nx - s * (sinA * r1) * ny,
         p1[1] + (cosA * r1) * ny + s * (sinA * r1) * nx];