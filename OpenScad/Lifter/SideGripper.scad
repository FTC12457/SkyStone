


$fn = 72;

r1 = 12;
p1 = [16,60];
p3 = [58,0];
r3 = 8;
r5 = r3;

r4outer = 25;
r4inner = 5;

GrabberLength=12;
Thickness = 4;
TipThickness = 10;
Chamfer = 0.5;


p2 = [(p1.x + p3.x)/2, (p1.y + p3.y)/2];

dxy = [p3.x - p1.x, p3.y - p1.y];
lxy = sqrt(dxy.x*dxy.x + dxy.y*dxy.y);
nxy = [-dxy.y/lxy, dxy.x/lxy];

p4 = [46, 30];

p5 = [p3.x, p3.y-GrabberLength];

p14 = RRTangent(p1,p4,r1,r4outer,1);
p41 = RRTangent(p4,p1,r4outer,r1,-1);
p43o = RRTangent(p4,p3,r4outer,r3,1);
p34o = RRTangent(p3,p4,r3,r4outer,-1);

p34 = RLTangent(p3,p4,r3,r4inner,1);
p43 = RLTangent(p4,p3,r4inner,r3,1);

p41i = RLTangent(p4,p1,r4inner,r1,-1);
p14i = RLTangent(p1,p4,r1,r4inner,-1);


difference() {
    union() {
        linear_extrude(height = Thickness)
            polygon([p1,p14,p41,p43o,p34o,p3,p34, p43, p41i, p14i]);
        
        translate([p1.x,p1.y,0])
            cylinder(r=r1,h=Thickness);
        translate([p3.x,p3.y,0])
            cylinder(r=r3,h=TipThickness);
        translate([p5.x,p5.y,0])
            cylinder(r=r5,h=TipThickness);
        
        translate([p3.x- r3, p3.y - GrabberLength, 0])
            cube([r3*2,GrabberLength,TipThickness]);
        
        intersection() {
            linear_extrude(height = TipThickness)
                polygon([p43o,p34o,p3,p34, p43]);
            
            translate([p3.x,p3.y,Thickness])
                cylinder(r1 = r3 + 4, r2 = r3, h = (TipThickness - Thickness));
        }
 
        intersection() {
            translate([0,0,-1])
                linear_extrude(height = Thickness + 2)
                    polygon([p41, p4, p43o, [300, 300]]);
            translate([p4.x,p4.y,0])
                cylinder(r=r4outer,h=Thickness);
     }
        
    }
    union () {
        translate([p4.x,p4.y,-1])
            cylinder(r=r4inner,h=Thickness+2);
        translate([p1.x,p1.y,0])
            TappedHole(3, Thickness);
        
        translate([p1.x,p1.y,0])
        rotate([0,0,0]) {
            translate([8,0,0])
                TappedHole(2, Thickness);
            translate([-8,0,0])
                TappedHole(2, Thickness);
            translate([0,+8,0])
                TappedHole(2, Thickness);
            translate([0,-8,0])
                TappedHole(2, Thickness);
        }
        
        translate([p4.x + nxy.x * (r4inner+r4outer)/2,
                   p4.y + nxy.y * (r4inner+r4outer)/2, 0])
            TappedHole(2, Thickness);
        translate([p3.x, p3.y - GrabberLength/2, 0])
            TappedHole(2, TipThickness);
    }
}

module TappedHole(r, thickness) {
    translate([0,0,-Chamfer])
        cylinder(r1=r+Chamfer*2, r2 = r, h = Chamfer*2);
    translate([0,0,-1])
    cylinder(r=r,h=thickness+2);
    translate([0,0,thickness - Chamfer])
        cylinder(r1=r, r2 = r+Chamfer*2, h = Chamfer*2);
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


function RLTangent(p1, p2, r1, r2, s) =
     let(dx = (p2[0] - p1[0]))
     let(dy = (p2[1] - p1[1]))
     let(distance2 = dx*dx + dy*dy)
     let(distance = sqrt(distance2))
     let(angleA = atan2(dy, dx))
     let(angleB = acos((r1+r2)/distance))
        [p1[0] + cos(angleA + angleB * s) * r1,
         p1[1] + sin(angleA + angleB * s) * r1];

