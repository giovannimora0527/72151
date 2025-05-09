package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Categoria;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.model.LibroRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.repository.CategoriaRepository;
import com.uniminuto.biblioteca.repository.LibroRepository; // Asume que extiende JpaRepository
import com.uniminuto.biblioteca.services.AutorService; // Asume que tiene obtenerAutorPorId
import com.uniminuto.biblioteca.services.LibroService; // Asume que declara save(Libro) y obtenerLibroId(Integer)
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importar Transactional si lo usas

/**
 * Implementación del servicio para la gestión de libros.
 * @author lmora
 */
@Service
public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroRepository libroRepository; // Asume que tiene findById, save, existsByTitulo, findByAutor, findByTitulo, findByAnioPublicacionBetween

    @Autowired
    private AutorService autorService; // Asume que tiene obtenerAutorPorId(Integer)

    @Autowired
    private CategoriaRepository categoriaRepository; // Asume que tiene findById

    @Override
    @Transactional(readOnly = true) // Anotación para transacciones de solo lectura
    public List<Libro> listarLibros() throws BadRequestException {
         try {
            return this.libroRepository.findAll(); // Método de JpaRepository
         } catch (Exception e) {
            throw new BadRequestException("Error al listar libros: " + e.getMessage());
         }
    }

    @Override
    @Transactional(readOnly = true)
    public Libro obtenerLibroId(Integer libroId) throws BadRequestException {
        // Método findById es de JpaRepository, devuelve Optional
        Optional<Libro> optLibro = this.libroRepository.findById(libroId);
        if (!optLibro.isPresent()) {
            throw new BadRequestException("No se encuentra el libro con el id = "
                    + libroId);
        }
        return optLibro.get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Libro> obtenerLibrosPorAutor(Integer autorId)
            throws BadRequestException {
        if (autorId == null) {
            throw new BadRequestException("El id del autor no puede ser vacio.");
        }
        // Asume que autorService.obtenerAutorPorId(Integer) devuelve Autor o lanza excepción
        Autor autor = this.autorService.obtenerAutorPorId(autorId);
        if (autor == null) { // O si obtenerAutorPorId lanza excepción, este null check no sería necesario
            throw new BadRequestException("El autor con el id ingresado no existe.");
        }
        // Asume que LibroRepository tiene findByAutor(Autor)
        List<Libro> librosAutor = this.libroRepository.findByAutor(autor);
        return !librosAutor.isEmpty() ? librosAutor : Collections.emptyList(); // Usar Collections.emptyList()
    }

    @Override
    @Transactional(readOnly = true)
    public Libro obtenerLibroPorNombre(String nombreLibro) throws BadRequestException {
        if (nombreLibro.isBlank() || nombreLibro.isEmpty()) {
            throw new BadRequestException("El nombre del libro es obligatorio.");
        }
        // Asume que LibroRepository tiene findByTitulo(String) que devuelve Libro o null
        Libro libro = this.libroRepository.findByTitulo(nombreLibro);
        if (libro == null) {
            throw new BadRequestException("No existe el libro con el nombre de "
                    + nombreLibro + ".");
        }
        return libro;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Libro> obtenerLibroXRangoPublicacion(
            Integer anioIni, Integer anioFin)
            throws BadRequestException {
        if (anioIni == null) { // Cambiado nombre de parámetro para claridad
            throw new BadRequestException("La fecha de inicio es obligatoria.");
        }
        if (anioFin == null) { // Cambiado nombre de parámetro para claridad
            throw new BadRequestException("La fecha final es obligatoria.");
        }

        if (anioFin < anioIni) {
            throw new BadRequestException("La fecha final no puede ser menor que la inicial.");
        }
        // Asume que LibroRepository tiene findByAnioPublicacionBetween(Integer, Integer)
        return this.libroRepository.findByAnioPublicacionBetween(anioIni, anioFin);
    }

    /**
     * Crea un nuevo libro en la base de datos.
     * @param libroRq Objeto con los datos para crear el libro.
     * @return Respuesta genérica indicando el resultado de la operación.
     * @throws BadRequestException Si la solicitud es inválida o el libro ya existe.
     */
    @Override
    @Transactional // Anotación para transacciones de escritura
    public RespuestaGenericaRs crearLibro(LibroRq libroRq) throws BadRequestException {
        // Paso 1. - en la bd si el libro existe por nombre
        // Paso 2. SI ESTA => lanzo el error
        // Paso 3. SINO esta Convertir mi objeto entrada rq a entidad Libro
        // Paso 4. Guardo el registro
        // Paso 5. Devolver una respuesta

        // Asume que LibroRepository tiene existsByTitulo(String)
        if (this.libroRepository.existsByTitulo(libroRq.getTitulo())) {
            throw new BadRequestException("El libro se encuentra ya registrado");
        }

        Libro libroGuardar = this.convertirLibroRqToLibro(libroRq);
        this.libroRepository.save(libroGuardar); // Llama al save del repositorio
        RespuestaGenericaRs rta = new RespuestaGenericaRs();
        rta.setMessage("Se ha guardado el libro satisfactoriamente");
        return rta;
    }

    /**
     * Convierte un objeto LibroRq a una entidad Libro.
     * @param libroRq Objeto LibroRq de entrada.
     * @return Entidad Libro convertida.
     * @throws BadRequestException Si el autor o la categoría no existen.
     */
    private Libro convertirLibroRqToLibro(LibroRq libroRq) throws BadRequestException {
        Libro libro = new Libro();
        libro.setAnioPublicacion(libroRq.getAnioPublicacion());

        // Asume que autorService.obtenerAutorPorId(Integer) devuelve Autor o lanza excepción
        Autor autor = this.autorService.obtenerAutorPorId(libroRq.getAutorId());
        if (autor == null) { // O si obtenerAutorPorId lanza excepción, este null check no sería necesario
            throw new BadRequestException("No existe el autor con el ID proporcionado.");
        }

        // Asume que CategoriaRepository tiene findById(Integer) que devuelve Optional
        Optional<Categoria> optCat = this.categoriaRepository.findById(libroRq.getCategoriaId());
        if (!optCat.isPresent()) {
            throw new BadRequestException("No existe la categoria con el ID proporcionado.");
        }
        Categoria categoria = optCat.get();

        libro.setAutor(autor);
        libro.setCategoria(categoria);
        libro.setTitulo(libroRq.getTitulo());
        libro.setExistencias(libroRq.getExistencias());
        return libro;
    }

    /**
     * Actualiza un libro existente en la base de datos.
     * @param actualizarLibro La entidad Libro con los datos actualizados.
     * @return Respuesta genérica indicando el resultado de la operación.
     * @throws BadRequestException Si el libro no existe, el título ya está en uso, o el autor/categoría no existen.
     */
    @Override
    @Transactional // Anotación para transacciones de escritura
    public RespuestaGenericaRs actualizarLibro(Libro actualizarLibro) throws BadRequestException {
        // Asume que LibroRepository tiene findById(Integer) que devuelve Optional
        Optional<Libro> optLibro = this.libroRepository.findById(actualizarLibro.getIdLibro());
        if (!optLibro.isPresent()) {
            throw new BadRequestException("No existe el libro con el ID proporcionado.");
        }

        Libro libroActual = optLibro.get();

        RespuestaGenericaRs rta = new RespuestaGenericaRs();
        rta.setMessage("Se ha actualizado el registro satisfactoriamente."); // Mensaje por defecto

        // Verifica si hay cambios reales antes de actualizar
        if (!hayCambiosEnLibro(libroActual, actualizarLibro)) {
            return rta; // Devuelve el mensaje por defecto si no hay cambios
        }

        // Si cambió el título, valida si ya existe otro libro con ese título (excluyendo el actual)
        // Asume que LibroRepository tiene existsByTitulo(String)
        if (!libroActual.getTitulo().trim().equalsIgnoreCase(actualizarLibro.getTitulo().trim())
                 && this.libroRepository.existsByTitulo(actualizarLibro.getTitulo())) {
             throw new BadRequestException("Ya existe un libro con el título '" + actualizarLibro.getTitulo() + "'.");
         }


        // Valida el autor
        if (actualizarLibro.getAutor() == null || actualizarLibro.getAutor().getAutorId() == null) {
            throw new BadRequestException("Debe especificar un autor válido.");
        }
        // Asume que autorService.obtenerAutorPorId(Integer) devuelve Autor o lanza excepción
        Autor autor = this.autorService.obtenerAutorPorId(actualizarLibro.getAutor().getAutorId());
        if (autor == null) { // O si obtenerAutorPorId lanza excepción, este null check no sería necesario
            throw new BadRequestException("No existe el autor con ID " + actualizarLibro.getAutor().getAutorId());
        }

        // Valida la categoría
        if (actualizarLibro.getCategoria() == null || actualizarLibro.getCategoria().getCategoriaId() == null) {
            throw new BadRequestException("Debe especificar una categoría válida.");
        }
        // Asume que CategoriaRepository tiene findById(Integer) que devuelve Optional
        Optional<Categoria> optCat = this.categoriaRepository.findById(actualizarLibro.getCategoria().getCategoriaId());
        if (!optCat.isPresent()) {
            throw new BadRequestException("No existe la categoría con ID " + actualizarLibro.getCategoria().getCategoriaId());
        }
        Categoria categoria = optCat.get();

        // Actualiza los campos de la entidad existente
        libroActual.setTitulo(actualizarLibro.getTitulo());
        libroActual.setAnioPublicacion(actualizarLibro.getAnioPublicacion());
        libroActual.setAutor(autor); // Asigna el objeto Autor obtenido
        libroActual.setCategoria(categoria); // Asigna el objeto Categoria obtenido
        libroActual.setExistencias(actualizarLibro.getExistencias());

        this.libroRepository.save(libroActual); // Llama al save del repositorio con la entidad actualizada

        // Si llegamos aquí, la actualización fue exitosa y hubo cambios
        rta.setMessage("Se ha actualizado el libro satisfactoriamente.");
        return rta;
    }

    /**
     * Compara dos entidades Libro para determinar si hay cambios relevantes para la actualización.
     * @param actual La entidad Libro actual de la base de datos.
     * @param nuevo La entidad Libro con los datos propuestos para actualizar.
     * @return true si hay cambios, false si son iguales.
     */
    private boolean hayCambiosEnLibro(Libro actual, Libro nuevo) {
         // Comprobación de nulidad para evitar NullPointerException
         if (nuevo == null) return false; // No hay cambios si el nuevo es nulo
         if (actual == null) return true; // Si el actual es nulo y el nuevo no, hay cambio (aunque esto no debería pasar si se busca por ID)


         // Comprobar cambios en campos primitivos/wrappers
         boolean cambiosSimples = !Objects.equals(actual.getTitulo(), nuevo.getTitulo()) ||
                                  !Objects.equals(actual.getAnioPublicacion(), nuevo.getAnioPublicacion()) ||
                                  !Objects.equals(actual.getExistencias(), nuevo.getExistencias());

         if (cambiosSimples) return true;

         // Comprobar cambios en relaciones (Autor y Categoria) por ID
         boolean cambiosRelaciones = false;

         // Comparar Autor por ID
         if (actual.getAutor() == null && nuevo.getAutor() != null) cambiosRelaciones = true;
         if (actual.getAutor() != null && nuevo.getAutor() == null) cambiosRelaciones = true;
         if (actual.getAutor() != null && nuevo.getAutor() != null &&
             !Objects.equals(actual.getAutor().getAutorId(), nuevo.getAutor().getAutorId())) {
             cambiosRelaciones = true;
         }

         if (cambiosRelaciones) return true;

         // Comparar Categoria por ID
         if (actual.getCategoria() == null && nuevo.getCategoria() != null) cambiosRelaciones = true;
         if (actual.getCategoria() != null && nuevo.getCategoria() == null) cambiosRelaciones = true;
         if (actual.getCategoria() != null && nuevo.getCategoria() != null &&
             !Objects.equals(actual.getCategoria().getCategoriaId(), nuevo.getCategoria().getCategoriaId())) {
             cambiosRelaciones = true;
         }

         return cambiosRelaciones; // Devuelve true si hubo cambios en relaciones
    }


    // *** IMPLEMENTACIÓN DEL MÉTODO save DECLARADO EN LA INTERFAZ ***
    // Este método es necesario porque la interfaz LibroService lo declara.
    @Override
    @Transactional // Anotación para transacciones de escritura
    public Libro save(Libro libro) {
        // Simplemente llama al método save del repositorio de libros
        return this.libroRepository.save(libro);
    }

    // *** Implementación del método findById declarado en la interfaz (si lo añadiste) ***
    // Si añadiste Optional<Libro> findById(Integer id) a la interfaz LibroService,
    // necesitas implementar ese método aquí también.
    // @Override
    // public Optional<Libro> findById(Integer id) {
    //     return this.libroRepository.findById(id);
    // }

}
