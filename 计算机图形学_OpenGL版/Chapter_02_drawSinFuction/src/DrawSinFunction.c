#include <GL/glut.h>
#include <math.h>
const int screenWidth = 640;
const int screenHeight = 480;
GLdouble A,B,C,D;
void init();
void display();

void init() {
	glClearColor(1.0, 1.0, 1.0, 0.0);
	glColor3f(0.0f, 0.0f, 0.0f);
	glPointSize(2.0f);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluOrtho2D(0, (GLdouble) screenWidth, 0, (GLdouble) screenHeight);
	A = (screenWidth / 2) / 3.14159365;
	B = screenWidth / 2;
	C = D = screenHeight / 2;
}

void display() {
	glClear(GL_COLOR_BUFFER_BIT);
	glBegin(GL_POINTS);
	GLdouble x = -3.14159265;
	for (x; x<3.14159365; x+=0.001) {
		GLdouble func = sin(x);//exp(-x)*cos(2*3.14159265*x);
		glVertex2d(A*x+B,C*func+D);
	}
	glEnd();
	glFlush();
}

void main(int argc, char* argv[]) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_RGB | GLUT_SINGLE);
	glutInitWindowPosition(100, 150);
	glutInitWindowSize(640, 480);

	glutCreateWindow("OpenGL 3D View");

	init();
	glutDisplayFunc(display);

	glutMainLoop();
}
