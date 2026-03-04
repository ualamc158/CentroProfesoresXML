import model.Asignatura;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utilidades.HibernateUtil;
import org.iesalandalus.programacion.utilidades.Entrada;

import model.Profesor;
import model.Centro;
import model.Especialidad;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            System.out.print("Elige una opción: ");
            opcion = Entrada.entero();

            switch (opcion) {
                case 1: altaProfesor(); break;
                case 2: bajaProfesor(); break;
                case 3: informacionProfesor(); break;
                case 4: listadoProfesores(); break;
                case 5: altaCentro(); break;
                case 6: bajaCentro(); break;
                case 7: modificarDirectorCentro(); break;
                case 8: listadoCentros(); break;
                case 9: altaEspecialidad(); break;
                case 10: bajaEspecialidad(); break;
                case 11: modificarNombreEspecialidad(); break;
                case 12: listadoEspecialidades(); break;
                case 13: altaAsignatura(); break;
                case 14: bajaAsignatura(); break;
                case 15: listadoAsignaturasYProfesores(); break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 0);

        HibernateUtil.getSessionFactory().close();
    }

    private static void mostrarMenu() {
        System.out.println("\n--- GESTIÓN DE CENTRO DE PROFESORES ---");
        System.out.println("1. Alta de un profesor");
        System.out.println("2. Baja de un profesor");
        System.out.println("3. Información de un profesor");
        System.out.println("4. Listado de profesores");
        System.out.println("5. Alta de un centro");
        System.out.println("6. Baja de un centro");
        System.out.println("7. Modificación del director del centro");
        System.out.println("8. Listado de centros");
        System.out.println("9. Alta de una especialidad");
        System.out.println("10. Baja de una especialidad");
        System.out.println("11. Modificación del nombre de una especialidad");
        System.out.println("12. Listado de especialidades");
        System.out.println("13. Alta de una asignatura");
        System.out.println("14. Baja de una asignatura");
        System.out.println("15. Listado de asignaturas y sus profesores");
        System.out.println("0. Salir");
    }

    // MÉTODO 1: ALTA DE PROFESOR
    private static void altaProfesor() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            System.out.println("\n--- ALTA DE PROFESOR ---");
            System.out.print("Introduce el código del profesor (ej. 4000): ");
            int cod = Entrada.entero();

            if (session.get(Profesor.class, cod) != null) {
                System.out.println("Ya existe un profesor con ese código.");
                return;
            }

            System.out.print("Nombre y Apellidos: ");
            String nombre = Entrada.cadena();

            System.out.print("Sexo (H/M): ");
            String sexo = Entrada.cadena();

            Date fechaNac = leerFecha("Fecha de nacimiento (yyyy-MM-dd): ");

            Profesor nuevoProfesor = new Profesor();
            nuevoProfesor.setCodProf(cod);
            nuevoProfesor.setNombreApe(nombre);
            nuevoProfesor.setSexo(sexo);
            nuevoProfesor.setFechaNac(fechaNac);

            System.out.print("Código de Especialidad (ej. IF, MT, LG): ");
            String codEspe = Entrada.cadena();
            Especialidad espe = session.get(Especialidad.class, codEspe);
            if (espe != null) {
                nuevoProfesor.setEspecialidad(espe);
            } else {
                System.out.println("Aviso: Especialidad no encontrada, se dejará en blanco.");
            }

            System.out.print("Código del Centro (ej. 1000): ");
            int codCentro = Entrada.entero();
            Centro centro = session.get(Centro.class, codCentro);
            if (centro != null) {
                nuevoProfesor.setCentro(centro);
            } else {
                System.out.println("Aviso: Centro no encontrado, se dejará en blanco.");
            }

            tx = session.beginTransaction();
            session.save(nuevoProfesor);
            tx.commit();
            System.out.println("¡Profesor dado de alta con éxito!");

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Error al dar de alta al profesor: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // MÉTODO 2: BAJA DE PROFESOR
    private static void bajaProfesor() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            System.out.println("\n--- BAJA DE PROFESOR ---");
            System.out.print("Introduce el código del profesor a borrar: ");
            int cod = Entrada.entero();

            Profesor profesor = session.get(Profesor.class, cod);

            if (profesor != null) {
                tx = session.beginTransaction();
                session.delete(profesor);
                tx.commit();
                System.out.println("Profesor eliminado correctamente.");
            } else {
                System.out.println("No se ha encontrado ningún profesor con el código " + cod);
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Error al eliminar el profesor. Puede que tenga dependencias: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // MÉTODO AUXILIAR PARA LEER FECHAS
    private static Date leerFecha(String mensaje) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        while (true) {
            System.out.print(mensaje);
            String fechaStr = Entrada.cadena();
            try {
                return sdf.parse(fechaStr);
            } catch (ParseException e) {
                System.out.println("Error: Formato de fecha incorrecto. Usa el formato yyyy-MM-dd.");
            }
        }
    }

    // MÉTODO 3: INFORMACIÓN DE PROFESOR
    private static void informacionProfesor() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("\n--- INFORMACIÓN DE PROFESOR ---");
            System.out.print("Introduce el código del profesor: ");
            int cod = Entrada.entero();

            Profesor profesor = session.get(Profesor.class, cod);

            if (profesor != null) {
                System.out.println("\nDatos del Profesor:");
                System.out.println("Código: " + profesor.getCodProf());
                System.out.println("Nombre: " + profesor.getNombreApe());
                System.out.println("Sexo: " + profesor.getSexo());
                System.out.println("Fecha Nacimiento: " + profesor.getFechaNac());

                String especialidad = (profesor.getEspecialidad() != null) ? profesor.getEspecialidad().getNombreEspe() : "Ninguna";
                System.out.println("Especialidad: " + especialidad);

                String centro = (profesor.getCentro() != null) ? profesor.getCentro().getNomCentro() : "Ninguno";
                System.out.println("Centro: " + centro);

                String jefe = (profesor.getJefe() != null) ? profesor.getJefe().getNombreApe() : "No tiene jefe";
                System.out.println("Jefe: " + jefe);
            } else {
                System.out.println("Profesor no encontrado.");
            }
        } finally {
            session.close();
        }
    }

    // MÉTODO 4: LISTADO DE PROFESORES
    private static void listadoProfesores() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("\n--- LISTADO DE PROFESORES ---");
            List<Profesor> lista = session.createQuery("FROM Profesor", Profesor.class).getResultList();

            for (Profesor p : lista) {
                System.out.printf("Cód: %d | Nombre: %s | Sexo: %s%n", p.getCodProf(), p.getNombreApe(), p.getSexo());
            }
            System.out.println("Total: " + lista.size());
        } finally {
            session.close();
        }
    }

    // MÉTODO 5: ALTA DE CENTRO
    private static void altaCentro() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            System.out.println("\n--- ALTA DE CENTRO ---");
            System.out.print("Código del centro: ");
            int cod = Entrada.entero();

            if (session.get(Centro.class, cod) != null) {
                System.out.println("El centro ya existe.");
                return;
            }

            Centro c = new Centro();
            c.setCodCentro(cod);
            System.out.print("Nombre: ");
            c.setNomCentro(Entrada.cadena());
            System.out.print("Dirección: ");
            c.setDireccion(Entrada.cadena());
            System.out.print("Localidad: ");
            c.setLocalidad(Entrada.cadena());
            System.out.print("Provincia: ");
            c.setProvincia(Entrada.cadena());

            tx = session.beginTransaction();
            session.save(c);
            tx.commit();
            System.out.println("Centro guardado.");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Error: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // MÉTODO 6: BAJA DE CENTRO
    private static void bajaCentro() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            System.out.println("\n--- BAJA DE CENTRO ---");
            System.out.print("Código del centro a borrar: ");
            int cod = Entrada.entero();

            Centro c = session.get(Centro.class, cod);
            if (c != null) {
                tx = session.beginTransaction();
                session.delete(c);
                tx.commit();
                System.out.println("Centro eliminado.");
            } else {
                System.out.println("Centro no encontrado.");
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Error: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // MÉTODO 7: MODIFICACIÓN DEL DIRECTOR DEL CENTRO
    private static void modificarDirectorCentro() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            System.out.println("\n--- MODIFICAR DIRECTOR ---");
            System.out.print("Código del centro: ");
            Centro c = session.get(Centro.class, Entrada.entero());

            if (c == null) {
                System.out.println("Centro no encontrado.");
                return;
            }

            System.out.print("Código del profesor (nuevo director): ");
            Profesor p = session.get(Profesor.class, Entrada.entero());

            if (p == null) {
                System.out.println("Profesor no encontrado.");
                return;
            }

            tx = session.beginTransaction();
            c.setDirector(p);
            session.update(c);
            tx.commit();
            System.out.println("Director actualizado.");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Error: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // MÉTODO 8: LISTADO DE CENTROS
    private static void listadoCentros() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("\n--- LISTADO DE CENTROS ---");
            String hql = "SELECT c.codCentro, c.nomCentro, d.nombreApe, size(c.profesores), count(distinct a) " +
                    "FROM Centro c LEFT JOIN c.director d LEFT JOIN c.profesores p LEFT JOIN p.asignaturas a " +
                    "GROUP BY c.codCentro, c.nomCentro, d.nombreApe";

            List<Object[]> resultados = session.createQuery(hql).getResultList();

            for (Object[] fila : resultados) {
                String director = (fila[2] != null) ? (String) fila[2] : "Sin director";
                System.out.printf("Cód: %d | Centro: %s | Director: %s | Nº Profes: %d | Nº Asig: %d%n",
                        fila[0], fila[1], director, fila[3], fila[4]);
            }
        } finally {
            session.close();
        }
    }

    // MÉTODO 9: ALTA DE ESPECIALIDAD
    private static void altaEspecialidad() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            System.out.println("\n--- ALTA DE ESPECIALIDAD ---");
            System.out.print("Código (2 letras): ");
            String cod = Entrada.cadena();

            if (session.get(Especialidad.class, cod) != null) {
                System.out.println("La especialidad ya existe.");
                return;
            }

            Especialidad e = new Especialidad();
            e.setIdEspecialidad(cod);
            System.out.print("Nombre: ");
            e.setNombreEspe(Entrada.cadena());

            tx = session.beginTransaction();
            session.save(e);
            tx.commit();
            System.out.println("Especialidad guardada.");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Error: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // MÉTODO 10: BAJA DE ESPECIALIDAD
    private static void bajaEspecialidad() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            System.out.println("\n--- BAJA DE ESPECIALIDAD ---");
            System.out.print("Código de la especialidad: ");
            Especialidad e = session.get(Especialidad.class, Entrada.cadena());

            if (e != null) {
                tx = session.beginTransaction();
                session.delete(e);
                tx.commit();
                System.out.println("Especialidad eliminada.");
            } else {
                System.out.println("Especialidad no encontrada.");
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Error: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // MÉTODO 11: MODIFICACIÓN DEL NOMBRE DE ESPECIALIDAD
    private static void modificarNombreEspecialidad() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            System.out.println("\n--- MODIFICAR ESPECIALIDAD ---");
            System.out.print("Código de la especialidad: ");
            Especialidad e = session.get(Especialidad.class, Entrada.cadena());

            if (e == null) {
                System.out.println("Especialidad no encontrada.");
                return;
            }

            System.out.print("Nuevo nombre: ");
            e.setNombreEspe(Entrada.cadena());

            tx = session.beginTransaction();
            session.update(e);
            tx.commit();
            System.out.println("Nombre actualizado.");
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            System.out.println("Error: " + ex.getMessage());
        } finally {
            session.close();
        }
    }

    // MÉTODO 12: LISTADO DE ESPECIALIDADES
    private static void listadoEspecialidades() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("\n--- LISTADO DE ESPECIALIDADES ---");
            List<Especialidad> lista = session.createQuery("FROM Especialidad", Especialidad.class).getResultList();
            for (Especialidad e : lista) {
                System.out.printf("Cód: %s | Nombre: %s%n", e.getIdEspecialidad(), e.getNombreEspe());
            }
        } finally {
            session.close();
        }
    }

    // MÉTODO 13: ALTA DE ASIGNATURA
    private static void altaAsignatura() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            System.out.println("\n--- ALTA DE ASIGNATURA ---");
            System.out.print("Código: ");
            String cod = Entrada.cadena();

            if (session.get(Asignatura.class, cod) != null) {
                System.out.println("La asignatura ya existe.");
                return;
            }

            Asignatura a = new Asignatura();
            a.setCodAsig(cod);
            System.out.print("Nombre: ");
            a.setNombreAsi(Entrada.cadena());

            tx = session.beginTransaction();
            session.save(a);
            tx.commit();
            System.out.println("Asignatura guardada.");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Error: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // MÉTODO 14: BAJA DE ASIGNATURA
    private static void bajaAsignatura() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            System.out.println("\n--- BAJA DE ASIGNATURA ---");
            System.out.print("Código de la asignatura: ");
            Asignatura a = session.get(Asignatura.class, Entrada.cadena());

            if (a != null) {
                tx = session.beginTransaction();
                session.delete(a);
                tx.commit();
                System.out.println("Asignatura eliminada.");
            } else {
                System.out.println("Asignatura no encontrada.");
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Error: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // MÉTODO 15: LISTADO DE ASIGNATURAS Y PROFESORES
    private static void listadoAsignaturasYProfesores() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("\n--- ASIGNATURAS Y SUS PROFESORES ---");
            List<Asignatura> lista = session.createQuery("FROM Asignatura", Asignatura.class).getResultList();

            for (Asignatura a : lista) {
                int numProfes = (a.getProfesores() != null) ? a.getProfesores().size() : 0;
                System.out.printf("\nAsignatura: %s - %s (Profesores: %d)%n", a.getCodAsig(), a.getNombreAsi(), numProfes);

                if (numProfes > 0) {
                    for (Profesor p : a.getProfesores()) {
                        String espe = (p.getEspecialidad() != null) ? p.getEspecialidad().getNombreEspe() : "Sin especialidad";
                        String jefe = (p.getJefe() != null) ? p.getJefe().getNombreApe() : "Sin jefe";
                        String centro = (p.getCentro() != null) ? p.getCentro().getNomCentro() : "Sin centro";

                        System.out.printf("  -> %s | Esp: %s | Jefe: %s | Centro: %s%n",
                                p.getNombreApe(), espe, jefe, centro);
                    }
                }
            }
        } finally {
            session.close();
        }
    }
}