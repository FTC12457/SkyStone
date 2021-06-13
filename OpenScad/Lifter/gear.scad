TotalThickness = 8;


Separation = 31.75;
Teeth1 = 12;
Teeth2 = 12;
Chamfer=0.5;

$fn = 72;

thickness = TotalThickness - Chamfer*2;

// The pitch (radius/teeth) needs to be the same for both gears.
r1 = Separation * Teeth1 / (Teeth1 + Teeth2);
r2 = Separation - r1;

echo(r1, r1/Teeth1);
echo(r2, r2/Teeth2);
p1 = InvoluteGear(Teeth1, r1, 25, 0.0);
p2 = InvoluteGear(Teeth2, r2, 25, 0.0);


a = 0;
 /*   
rotate([0,0,-a])
    polygon(p1);
    translate([32,0,0])
        rotate([0,0,a])
            color("red") polygon(p2);*/
  

Gear(p1);


module Gear(p) {
    difference() {
        union() {
        s = (Separation - 2*Chamfer) / Separation;
            
        translate([0,0,0])
            scale([s,s,1])
                linear_extrude(height=Chamfer, scale=1.0/s)
                    polygon(p);
            
        translate([0,0,Chamfer])
            linear_extrude(height=thickness)
                polygon(p);
            
        translate([0,0,TotalThickness - Chamfer])
            linear_extrude(height=Chamfer, scale = s)
                polygon(p);

        }
        union () {
            TappedHole(4);
            translate([8,0,0])
                TappedHole(2);
            translate([-8,0,0])
                TappedHole(2);
            translate([0,8,0])
                TappedHole(2);
            translate([0,-8,0])
                TappedHole(2);
        }
    }
}

module TappedHole(r) {
    translate([0,0, -Chamfer])
        cylinder(r1=r+Chamfer*2, r2 = r, h = Chamfer*2);
    
    translate([0,0,-1])
        cylinder(r=r,h=TotalThickness+2);
    translate([0,0,TotalThickness - Chamfer])
        cylinder(r1=r, r2 = r+Chamfer*2, h = Chamfer*2);
}

function FromAngle(a) =
        let(r = a * 180 / PI)
        let(c = cos(r))
        let(s = sin(r))
            [c, s];
        
function Normal(p) = [p[1], -p[0]];        
function Length2(p) = pow(p[0],2) + pow(p[1],2);
function Times(p, s) = [p[0] * s, p[1] * s];
function Plus(p1,p2) = [p1[0] + p2[0], p1[1] + p2[1]];
function Rotate(p, a) =
        let(r = a * 180 / PI)
        let(c = cos(r))
        let(s = sin(r))
            [p[0] * c - p[1] * s, p[0] * s + p[1] * c];       
 
function InvoluteGear(numTeeth, pitchRadius, pressureAngle, clearance) =
    let(tooth = InvoluteTooth(numTeeth, pitchRadius, pressureAngle, clearance))
    [
        for(i = [0:numTeeth-1])
            let(twist = 2.0*PI/numTeeth)
            for(j = [0:len(tooth)-1])
                Rotate(tooth[j], twist*i)
    ];
                

        
function InvoluteTooth(numTeeth, pitchRadius, pressureAngle, clearance) =
    let(circularPitch = (pitchRadius * (2 * PI)) / numTeeth)
    let(addendum = circularPitch / PI)
    let(dedendum = addendum + clearance)
    let(rootRadius = pitchRadius - dedendum)
    let(arc = InvoluteToothArc(numTeeth, pitchRadius, pressureAngle, clearance))
    let(base = Intersect(arc[0], [rootRadius, 0], Times(FromAngle(-2*PI/numTeeth),rootRadius)))
        [
            base,
            for (i=[0:len(arc)-1]) arc[i],
            for (i=[len(arc)-1:-1:0]) [arc[i].x, -arc[i].y],
            [base.x, -base.y]
        ];

/*
  For gear terminology see: 
    http://www.astronomiainumbria.org/advanced_internet_files/meccanica/easyweb.easynet.co.uk/_chrish/geardata.htm
  Algorithm based on:
    http://www.cartertools.com/involute.html

  circularPitch: The distance between adjacent teeth measured at the pitch circle
*/
function InvoluteToothArc(numTeeth, pitchRadius, pressureAngle, clearance) =
    let(circularPitch = (pitchRadius * (2 * PI)) / numTeeth)
    let(addendum = circularPitch / PI)
    let(dedendum = addendum + clearance)
    let(baseRadius = pitchRadius * cos(pressureAngle)) // radiuses of the 3 circles:
    let(outerRadius = pitchRadius + addendum)
    let(rootRadius = pitchRadius - dedendum)
    let(maxtanlength = sqrt(outerRadius * outerRadius - baseRadius * baseRadius))
    let(maxangle = maxtanlength / baseRadius)        
    let(tl_at_pitchcircle = sqrt(pitchRadius * pitchRadius - baseRadius * baseRadius))
    let(angle_at_pitchcircle = tl_at_pitchcircle / baseRadius)
    let(diffangle = angle_at_pitchcircle - atan(angle_at_pitchcircle) * PI / 180)
    let(angularToothWidthAtBase = PI / numTeeth + 2 * diffangle)        
    let(toothAngle = 2 * PI / numTeeth)
    let(toothCenterAngle = 0.5 * angularToothWidthAtBase)
    let (resolution = 7)
        [for(i = [0:resolution])
            let(angle = maxangle * i / resolution)
            let(tanlength = angle * baseRadius)
            let(radvector = FromAngle(angle - toothCenterAngle))
            let(tanvector = Normal(radvector))
                Plus(Times(radvector, baseRadius),
                     Times(tanvector, tanlength))
        ];

function Intersect(p, p1, p2) =
    let(dx = p2.x - p1.x)
    let(dy = p2.y - p1.y)
    let(l = (dy * p1.x - dx * p1.y) / (dy * p.x - dx * p.y))
        [l * p.x, l * p.y ];

function RRTangent(p1, p2, r1, r2, s) =
     let(dx = (p2[0] - p1[0]))
     let(dy = (p2[1] - p1[1]))
     let(distance2 = dx*dx + dy*dy)
     let(distance = sqrt(distance2))
     let(nx = dx / distance)
     let(ny = dy / distance)
     let(cosA = (r1-r2)/distance)
     let(sinA = sqrt(1-cosA*cosA))
        [[for (i = [0:len(FixedCrossSection)-1]) i]];


function RLTangent(p1, p2, r1, r2, s) =
     let(dx = (p2[0] - p1[0]))
     let(dy = (p2[1] - p1[1]))
     let(distance2 = dx*dx + dy*dy)
     let(distance = sqrt(distance2))
     let(angleA = atan2(dy, dx))
     let(angleB = acos((r1+r2)/distance))
        [p1[0] + cos(angleA + angleB * s) * r1,
         p1[1] + sin(angleA + angleB * s) * r1];

