
GirderOffset = 4;           // 8 for thick rod        
GirderThickness = 2;        // 3 for thick girder
HoleSpacing = 32;           // 16 for thick girder
HoleRadius = 2;

WallThickness = 2;
NotchWidth = GirderThickness + 1;
NotchOffset = 0;
PoleHeight = NotchOffset+77;
NotchCut = 15;

CapLength = 78;
CapWidth = 110;
CapDepth = 4.425;

ChamferSize = 2;


CapThickness = 4;

PoleWidth = NotchWidth+6;

NotchSetback = WallThickness;
HoleOffset = WallThickness +
             GirderOffset +
             HoleRadius;

PoleDepth = HoleOffset + HoleRadius + WallThickness+2;
echo(PoleDepth+CapLength+CapThickness);


TextOffset = -12;
TextDepth = 0.49;
TextShift = 0;

difference() {
    union() {


        
        translate([-CapWidth/2,CapLength,0])
            cube([CapWidth, CapThickness, CapDepth]);
        translate([-PoleWidth/2,0,0])
            cube([PoleWidth, CapLength, CapDepth]);
        
        translate([0,0,CapDepth])
        rotate([0,-90,0])
        linear_extrude(height=PoleWidth, center=true)
            polygon([[0,0],
                      [ChamferSize,0],
                      [0,ChamferSize]]);
        
        linear_extrude(height=CapDepth)
            polygon([[-(ChamferSize+PoleWidth/2),CapLength],
                      [-(PoleWidth/2),CapLength-ChamferSize],
                      [PoleWidth/2,CapLength-ChamferSize],
                      [(ChamferSize+PoleWidth/2),CapLength]]);
        
        translate([-PoleWidth/2,-PoleDepth,0])
            cube([PoleWidth, PoleDepth, PoleHeight]);
            
    }
    
    union () {
   /*     translate([-NotchWidth/2, -(PoleDepth+NotchSetback), NotchOffset])
            cube([NotchWidth, PoleDepth, PoleHeight]);
        
        translate([0,-(PoleDepth+NotchSetback),NotchOffset])
        rotate([0,90,0])
        linear_extrude(height=NotchWidth, center=true)
            polygon([[-1,0], [-1,PoleDepth],
                     [0,PoleDepth],
                     [NotchCut,0]]);*/
        /*
        for (i = [0:2])
            translate([0,-HoleOffset,PoleHeight - i * HoleSpacing - 5])
                rotate([0,90,0]) {
                    translate([0,0,-PoleWidth/2])
                        cylinder(r1 = 4, r2 = 2, h = 2, $fn=72, center=true);
                    cylinder(r=2,h=PoleWidth+4, $fn=72, center=true);
                    translate([0,0,PoleWidth/2])
                        cylinder(r1 = 2, r2 = 4, h = 2, $fn=72, center=true);

                }
         */       
    
        translate([PoleWidth/2-TextDepth,TextOffset,PoleHeight-1 - TextShift])
            rotate([0,90,0])
                linear_extrude(height=1)        
                    text("1");
            
        translate([PoleWidth/2-TextDepth,TextOffset,PoleHeight-17 - TextShift])
            rotate([0,90,0])
                linear_extrude(height=1)        
                    text("2");
            
        translate([PoleWidth/2-TextDepth,TextOffset,PoleHeight-33 - TextShift])
            rotate([0,90,0])
                linear_extrude(height=1)        
                    text("5");
            
        translate([PoleWidth/2-TextDepth,TextOffset,PoleHeight-49 - TextShift])
            rotate([0,90,0])
                linear_extrude(height=1)        
                    text("4");
            
        translate([PoleWidth/2-TextDepth,TextOffset,PoleHeight-65 - TextShift])
            rotate([0,90,0])
                linear_extrude(height=1)        
                    text("7");
                    
        translate([-(PoleWidth/2)+TextDepth,TextOffset,PoleHeight-9 - TextShift])
            rotate([0,-90,0])
                linear_extrude(height=1)        
                    text("7");
            
        translate([-(PoleWidth/2)+TextDepth,TextOffset,PoleHeight-25 - TextShift])
            rotate([0,-90,0])
                linear_extrude(height=1)        
                    text("4");
            
        translate([-(PoleWidth/2)+TextDepth,TextOffset,PoleHeight-41 - TextShift])
            rotate([0,-90,0])
                linear_extrude(height=1)        
                    text("5");
            
        translate([-(PoleWidth/2)+TextDepth,TextOffset,PoleHeight-57 - TextShift])
            rotate([0,-90,0])
                linear_extrude(height=1)        
                    text("2");
            
        translate([-(PoleWidth/2)+TextDepth,TextOffset,PoleHeight-73 - TextShift])
            rotate([0,-90,0])
                linear_extrude(height=1)        
                    text("1");
    }
}

module RoundedRectangle(l,w,h,r) {
    translate([0,0,h/2])
        union() {
            cube([l, w-2*r, h], center=true);
            cube([l-2*r, w, h], center=true);
            translate([l/2 - r, w/2 - r, 0])
                cylinder(r=r, h=h, center=true, $fn = 72);
            translate([l/2 - r, r - w/2, 0])
                cylinder(r=r, h=h, center=true, $fn = 72);
            translate([r - l/2, w/2 - r, 0])
                cylinder(r=r, h=h, center=true, $fn = 72);
            translate([r - l/2, r - w/2, 0])
                cylinder(r=r, h=h, center=true, $fn = 72);
        }
}