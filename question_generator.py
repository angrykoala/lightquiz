#!/usr/bin/env python
import Tkinter,sys,sqlite3,ttk
from Tkinter import *


#Windows variable
top = Tkinter.Tk()
#variable global base de datos
conn = sqlite3.connect('test.db');

######funciones base de datos###############

#funcion para insertar un elemento con imagen
def insertQuestion(question,correctAnswer,answer1,answer2,answer3,genero):
	imagen="NULL";
	sonido="NULL";
	conn.execute("INSERT INTO LIGHTQUIZ (QUESTION,CA,A1,A2,A3,CATEGORY,SOUND_NAME,IMAGEN_NAME) VALUES ('"+question+"','"+correctAnswer+"','"+answer1+"','"+answer2+"','"+answer3+"','"+genero+"',"+sonido+","+imagen+")");



#funcion para insertar un elemento con imagen
def insertQuestion_img(question,correctAnswer,answer1,answer2,answer3,genero,imagen=None):
	imagen="'"+imagen+"'";
	sonido="NULL";
	conn.execute("INSERT INTO LIGHTQUIZ (QUESTION,CA,A1,A2,A3,CATEGORY,SOUND_NAME,IMAGEN_NAME) VALUES ('"+question+"','"+correctAnswer+"','"+answer1+"','"+answer2+"','"+answer3+"','"+genero+"',"+sonido+","+imagen+")");



#funcion para insertar un elemento con sonido
def insertQuestion_sonido(question,correctAnswer,answer1,answer2,answer3,genero,sonido=None):
	sonido="'"+sonido+"'";
	imagen="NULL";
	conn.execute("INSERT INTO LIGHTQUIZ (QUESTION,CA,A1,A2,A3,CATEGORY,SOUND_NAME,IMAGEN_NAME) VALUES ('"+question+"','"+correctAnswer+"','"+answer1+"','"+answer2+"','"+answer3+"','"+genero+"',"+sonido+","+imagen+")");




#funcion para crear la tabla de la base de datos, la crea si no existe
def creardatabase():
	print "Opened database successfully";
	conn.execute('''CREATE TABLE if not exists LIGHTQUIZ
	   (ID INTEGER PRIMARY KEY     AUTOINCREMENT,
	   QUESTION       TEXT NOT NULL,
	   CA		     TEXT    NOT NULL,
	   A1       TEXT    NOT NULL,
	   A2       TEXT    NOT NULL,
	   A3       TEXT    NOT NULL,
	   CATEGORY	    TEXT    NOT NULL,
	   SOUND_NAME	TEXT,
	   IMAGEN_NAME       TEXT);''');
	print "Table created successfully";


######funciones interfaz usuario###############
#funcion para mostrar los elementos en una ventana
def mostrar():
    text.delete(0, END)#borramos todo lo que tiene
    cursor = conn.execute("SELECT id,question,ca,a1,a2,a3,category,sound_name,imagen_name from LIGHTQUIZ");
    for row in cursor:
		text.insert(END, "ID =" + str(row[0]));
		text.insert(END, "QUESTION= "+row[1]);
		text.insert(END, "CA = "+ row[2]);
		text.insert(END,"A1 = "+ row[3]);
		text.insert(END, "A2 = "+ row[4]);
		text.insert(END, "A3 = "+ row[5]);
		text.insert(END, "CATEGORY = "+ row[6]);
		if row[7] is None:
			text.insert(END, "SOUND_NAME= ");
		else:
		    text.insert(END, "SOUND_NAME = "+ row[7]);
		if row[8] is None:
		    text.insert(END, "IMAGEN_NAME= ");
		else:
		    text.insert(END, "IMAGEN_NAME = "+ row[8]);
		text.insert(END,"");


#funcion para insertar una tupla
def insertar():
	if v1.get()!="" and v2.get()!="" and v3.get()!="" and v4.get()!=""  and v5.get()!="" and e6.get()!="Genero":#debe estar todos los campos rellenos
		if l7.get()=="No Multimedia":#si no hay imagne ni sonido
			insertQuestion(v1.get(),v2.get(),v3.get(),v4.get(),v5.get(),e6.get());
		else:#si hay imagen o sonido
			if l7.get()=="Sonido":
				insertQuestion_sonido(v1.get(),v2.get(),v3.get(),v4.get(),v5.get(),e6.get(),v7.get());#insertamos tupla con imagne
				l7.set("No Multimedia")
				e7.delete(0, END) #vacia el texto dle entri
			else:#hay imagen
		   		insertQuestion_img(v1.get(),v2.get(),v3.get(),v4.get(),v5.get(),e6.get(),v7.get());#insertamos tupla con sonido
				l7.set("No Multimedia")
				e7.delete(0, END) #vacia el texto dle entri

		e1.delete(0, END) #vacia el texto dle entri
		e2.delete(0, END) #vacia el texto dle entri
		e3.delete(0, END) #vacia el texto dle entri
		e4.delete(0, END) #vacia el texto dle entri
		e5.delete(0, END) #vacia el texto dle entri
		e6.set("Genero"); #vacia el texto dle entri
		conn.commit();



#elimina una tupla a partir de su ide
def eliminar():
	#quizas haya que pasar v1.get a int
	if v9.get()!="":
		conn.execute("DELETE FROM LIGHTQUIZ WHERE ID = ? ", (v9.get(),))
		e9.delete(0, END) #vacia el texto dle entri
		text.delete(0, END)#borramos todo lo que tiene
		conn.commit();

#funcion para salir
def salir():
    sys.exit(0)



#################programa#####################
top.wm_title("Gestor base datos");#fijamos nombre ventana
creardatabase();#creamso la tabla LIGHTQUIZ si no existe

##################anadir widget####################
#etiqueta y relleanr texto
l1 = Label(top, text="Pregunta").grid(row=0, column=0)
v1 = StringVar()
e1 = Entry(top, textvariable=v1,width=50)
e1.grid(row=0, column=1)


l2 = Label(top, text="Respuesta correcta").grid(row=1, column=0)
v2 = StringVar()
e2 = Entry(top, textvariable=v2,width=50)
e2.grid(row=1, column=1)

l3 = Label(top, text="Respuesta falsa").grid(row=2, column=0)
v3 = StringVar()
e3 = Entry(top, textvariable=v3,width=50)
e3.grid(row=2, column=1)

l4 = Label(top, text="Respuesta falsa").grid(row=3, column=0)
v4 = StringVar()
e4 = Entry(top, textvariable=v4,width=50)
e4.grid(row=3, column=1)

l5 = Label(top, text="Respuesta falsa").grid(row=4, column=0)
v5 = StringVar()
e5 = Entry(top, textvariable=v5,width=50)
e5.grid(row=4, column=1)

l6 = Label(top, text="genero").grid(row=5, column=0)
e6 = ttk.Combobox(values=["geografia", "ciencias", "arte", "deportes", "cine"])
e6.set("Genero")
e6.configure(width=48)
e6.grid(row=5, column=1)


l7 = ttk.Combobox(values=["Sonido", "Imagen"])
l7.set("No Multimedia")
l7.configure(width=15)
l7.grid(row=6, column=0)
v7 = StringVar()
e7 = Entry(top, textvariable=v7,width=50)
e7.grid(row=6, column=1)




l9 = Label(top, text="Eliminar por id").grid(row=7, column=0)
v9 = StringVar()
e9= Entry(top, textvariable=v9,width=50)
e9.grid(row=7, column=1)

#boton
b1= Tkinter.Button(top, text ="Insertar", command = insertar).grid(row=4, column=3)
b2 = Tkinter.Button(top, text ="Mostrar", command = mostrar).grid(row=5, column=3)
b3 = Tkinter.Button(top, text ="Eliminar", command = eliminar).grid(row=7, column=3)
b4 = Tkinter.Button(top, text ="Salir", command = salir).grid(row=8, column=3)

#text
scrollbar = Scrollbar(top)
scrollbar.grid(row=8, column=1)
text = Listbox(top,width=50,height=15,yscrollcommand=scrollbar.set)
text.grid(row=8, column=1)
scrollbar.config(command=text.yview)


top.mainloop()
