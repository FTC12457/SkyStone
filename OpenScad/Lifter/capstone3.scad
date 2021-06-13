$fn = 72;

SideDepth = 56;
StrawRadius = 6.5 / 2.0;
Thickness = 2;

OuterRadius = StrawRadius;
InnerRadius = OuterRadius-2;

StrawChamfer = 0.25;
StrawLength = 12;

HookCut = 6.5;
HookWidth = HookCut + 4;

SideLength = 90 - 2 * OuterRadius;
HookHeight = 8;


echo(SideDepth + 2 * OuterRadius);
echo(SideLength + 2 * OuterRadius);
intersection () {
    linear_extrude(height = 100)
        offset(OuterRadius)
            polygon([[0,0], [SideDepth, 0], [SideDepth, SideLength],
                     [0, SideLength]]);            
    

    difference () {
        union() {
            translate([-500,-500,0])
                cube([1000,1000,Thickness]);
            
            translate([HookHeight,-OuterRadius, Thickness]) {
                difference() {
                    union () {
                        color("green")
                        cube([SideDepth - 2 * HookHeight,
                              HookWidth, HookHeight]);
                        
                        rotate([-90,0,0])
                           cylinder(r = HookHeight, h = HookWidth);
                        translate([SideDepth - 2 * HookHeight,0,0])
                        rotate([-90,0,0])
                           cylinder(r = HookHeight, h = HookWidth);
                    }
                    
                    union () {
                        translate([-40, (HookWidth - HookCut)/2, 0])
                            cube([200, HookCut, HookHeight + 1]);
                        
                        translate([SideDepth/2 - HookHeight - 16,0,4.25])
                            rotate([-90,0,0])
                                TappedHole(1, HookWidth, 1);
                        translate([SideDepth/2 - HookHeight,0,4.25])
                            rotate([-90,0,0])
                                TappedHole(1, HookWidth, 1);
                        translate([SideDepth/2 - HookHeight + 16,0,4.25])
                            rotate([-90,0,0])
                                TappedHole(1, HookWidth, 1);
                    }
                }
            }
            
            translate([0, SideLength, Thickness]) {
                cylinder(r1 = StrawRadius + 0.5, r2 = StrawRadius,
                         h = 0.5);
                translate([0, 0, 0.5])
                    cylinder(r1 = StrawRadius, r2 = StrawRadius - StrawChamfer,
                             h = StrawLength - 0.5);
                translate([0, 0, StrawLength])
                    cylinder(r1 = StrawRadius - StrawChamfer,
                             r2 = StrawRadius - StrawChamfer - 0.5,
                             h = 0.5);
            }
            
        }
        union () {
            s = HookWidth + InnerRadius - OuterRadius;
            translate([0,s,-1])
            linear_extrude(height = Thickness + 20)
                offset(InnerRadius)
                    polygon([[0,0], [SideDepth, 0],
                             [SideDepth, SideLength-s],
                             [0, SideLength-s]]);
        }
    }
}

module TappedHole(r, h, c) {
    translate([0,0,-1])
        cylinder(r = r, h = h + 2);
    
    translate([0,0,-c])
        cylinder(r1 = r + 2*c, r2 = r, h = 2 * c);
    
    translate([0,0, h-c])
        cylinder(r1 = r, r2 = r + 2*c, h = 2 * c);
    
}