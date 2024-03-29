package br.ufrn.imd.utravel.repository;

import br.ufrn.imd.utravel.model.ViagemDestino;
import br.ufrn.imd.utravel.repository.mapper.ViagemDestinoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ViagemDestinoRepository {
    @Autowired
    private final JdbcTemplate jdbcTemplateObject;

    private final EstadiaRepository estadiaRepository;

    @Autowired
    public ViagemDestinoRepository(JdbcTemplate jdbcTemplateObject, EstadiaRepository estadiaRepository) {
        this.jdbcTemplateObject = jdbcTemplateObject;
        this.estadiaRepository = estadiaRepository;
    }

    public ViagemDestino findById(Integer id){
        String SQL = "SELECT * FROM utravel.viagem_destino vd, utravel.viagem v, utravel.localizacao l " +
                "WHERE vd.viagem_id = v.id AND vd.destino_id = l.id AND vd.id = ?";
        List<ViagemDestino> viagemDestinos = jdbcTemplateObject.query(SQL, new Object[]{ id }, new ViagemDestinoMapper());

        if (viagemDestinos.isEmpty()){
            return null;
        }

        viagemDestinos.get(0).setEstadias(estadiaRepository.findByViagemDestinoId(viagemDestinos.get(0).getId()));

        return viagemDestinos.get(0);
    }

    public void save(ViagemDestino viagemDestino){
        String SQL = "INSERT INTO utravel.viagem_destino (viagem_id, destino_id) VALUES (?, ?)";
        jdbcTemplateObject.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, viagemDestino.getViagem().getId());
            preparedStatement.setInt(2, viagemDestino.getLocalizacao().getId());
            return preparedStatement;
        });
    }

    public String delete(Integer id) {
        String SQL = "DELETE FROM utravel.viagem_destino WHERE id = ?";
        jdbcTemplateObject.update(SQL, id);

        return "Sucesso";
    }

    public List<ViagemDestino> findByViagemId(Integer viagemId){
        String SQL = "SELECT * FROM utravel.viagem_destino vd, utravel.viagem v, utravel.localizacao l " +
                        "WHERE vd.viagem_id = v.id AND vd.destino_id = l.id AND v.id = ?";
        List<ViagemDestino> viagemDestinos = jdbcTemplateObject.query(SQL, new Object[]{ viagemId }, new ViagemDestinoMapper());

        if (!viagemDestinos.isEmpty()){
            if (estadiaRepository.findByViagemDestinoId(viagemDestinos.get(0).getId()) != null){
                viagemDestinos.get(0).setEstadias(estadiaRepository.findByViagemDestinoId(viagemDestinos.get(0).getId()));
            }
        }

        return viagemDestinos;
    }
}
