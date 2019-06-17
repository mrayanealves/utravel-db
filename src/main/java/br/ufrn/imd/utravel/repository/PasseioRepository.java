package br.ufrn.imd.utravel.repository;

import br.ufrn.imd.utravel.model.Passeio;
import br.ufrn.imd.utravel.repository.mapper.PasseioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class PasseioRepository implements GenericRepository<Passeio> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PasseioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Passeio> findAll() {
        String sql = "SELECT * FROM utravel.passeio p";
        return jdbcTemplate.query(sql, new PasseioMapper());
    }

    @Override
    public Optional<Passeio> findById(Integer id) {
        String sql = "SELECT * FROM utravel.passeio p WHERE p.id = ?";
        List<Passeio> passeios = jdbcTemplate.query(sql, new Object[]{id}, new PasseioMapper());
        if(passeios.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(passeios.get(0));
    }

    @Override
    public Passeio save(Passeio passeio) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String SQL = "INSERT INTO utravel.passeio (tipo, endereco, empresa) VALUES (?, ?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, passeio.getTipo());
            preparedStatement.setString(2, passeio.getEndereco());
            preparedStatement.setString(3, passeio.getEmpresa());
            return preparedStatement;
        }, keyHolder);

        if (keyHolder.getKey() != null){
            passeio.setId(keyHolder.getKey().intValue());
        }

        return passeio;
    }

    @Override
    public Passeio update(Passeio passeio) {
        String SQL = "UPDATE utravel.passeio SET tipo = ?, endereco = ?, empresa = ? WHERE id = ?";
        jdbcTemplate.update(SQL,
                passeio.getTipo(),
                passeio.getEndereco(),
                passeio.getEmpresa(),
                passeio.getId());
        return passeio;
    }

    @Override
    public String delete(Integer id) {
        String SQL = "DELETE FROM utravel.passeio WHERE id = ?";
        jdbcTemplate.update(SQL, id);
        return "Sucesso";
    }
}