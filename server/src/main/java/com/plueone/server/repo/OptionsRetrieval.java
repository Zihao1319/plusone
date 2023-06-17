package com.plueone.server.repo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.plueone.server.models.Diet;
import com.plueone.server.models.Education;
import com.plueone.server.models.Horoscope;
import com.plueone.server.models.Interest;
import com.plueone.server.models.Language;
import com.plueone.server.models.Personality;
import com.plueone.server.models.Prompt;
import com.plueone.server.models.Race;
import com.plueone.server.models.Subinterest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class OptionsRetrieval {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String GET_ALL_RACES = "select * from race";

    private static final String GET_ALL_PROMPTS_SQL = "select * from prompt";

    private static final String GET_ALL_PERSONALITY_SQL = "select * from personality";

    private static final String GET_ALL_LANGUAGES_SQL = "select * from language";

    private static final String GET_ALL_HOROSCOPES_SQL = "select * from horoscope";

    private static final String GET_ALL_EDUCATION_SQL = "select * from education";

    private static final String GET_ALL_DIET_SQL = "select * from diet";

    private static final String GET_ALL_INTEREST_SQL = "select * from interest";

    private static final String GET_ALL_SUB_INTEREST_SQL = "select * from subInterest";

    public List<Race> getRaceOptions() {

        final List<Race> raceList = new ArrayList<Race>();

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_ALL_RACES);

        while (rs.next()) {
            Race race = new Race();
            race.setRaceId(rs.getInt("race_id"));
            race.setRaceName(rs.getString("name"));
            raceList.add(race);
        }
        return Collections.unmodifiableList(raceList);
    }

    public List<Prompt> getPromptOptions() {

        final List<Prompt> promptList = new ArrayList<Prompt>();

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_ALL_PROMPTS_SQL);

        while (rs.next()) {
            Prompt prompt = new Prompt();
            prompt.setPromptId(rs.getInt("prompt_id"));
            prompt.setPromptQuestion(rs.getString("name"));
            promptList.add(prompt);
        }
        return Collections.unmodifiableList(promptList);
    }

    public List<Personality> getPersonalityOptions() {

        final List<Personality> personalityList = new ArrayList<Personality>();

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_ALL_PERSONALITY_SQL);

        while (rs.next()) {
            Personality p = new Personality();
            p.setPersonalityId(rs.getInt("personality_id"));
            p.setPersonalityType(rs.getString("name"));
            personalityList.add(p);
        }
        return Collections.unmodifiableList(personalityList);
    }

    public List<Language> getLanguageOptions() {

        final List<Language> languageList = new ArrayList<Language>();

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_ALL_LANGUAGES_SQL);

        while (rs.next()) {
            Language l = new Language();
            l.setLanguageId(rs.getInt("language_id"));
            l.setLanguageName(rs.getString("name"));
            languageList.add(l);
        }
        return Collections.unmodifiableList(languageList);
    }

    public List<Horoscope> getHosorscopeOptions() {

        final List<Horoscope> horosList = new ArrayList<Horoscope>();

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_ALL_HOROSCOPES_SQL);

        while (rs.next()) {
            Horoscope h = new Horoscope();
            h.setHoroscopeId(rs.getInt("horoscope_id"));
            h.setName(rs.getString("name"));
            horosList.add(h);
        }
        return Collections.unmodifiableList(horosList);
    }

    public List<Education> getEducationOptions() {

        final List<Education> eduList = new ArrayList<Education>();

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_ALL_EDUCATION_SQL);

        while (rs.next()) {
            Education e = new Education();
            e.setEducationId(rs.getInt("education_id"));
            e.setName(rs.getString("name"));
            eduList.add(e);
        }
        return Collections.unmodifiableList(eduList);
    }

    public List<Diet> getDietOptions() {

        final List<Diet> dietList = new ArrayList<Diet>();

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_ALL_DIET_SQL);

        while (rs.next()) {
            Diet d = new Diet();
            d.setDietId(rs.getInt("diet_id"));
            d.setName(rs.getString("name"));
            dietList.add(d);
        }
        return Collections.unmodifiableList(dietList);
    }

    public List<Interest> getInterestOptions() {

        final List<Interest> iList = new ArrayList<Interest>();

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_ALL_INTEREST_SQL);

        while (rs.next()) {
            Interest i = new Interest();
            i.setInterestId(rs.getInt("interest_id"));
            i.setInterestName(rs.getString("name"));
            iList.add(i);
        }
        return Collections.unmodifiableList(iList);
    }

    public List<Subinterest> getSubInterestOptions() {

        final List<Subinterest> sList = new ArrayList<Subinterest>();

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_ALL_SUB_INTEREST_SQL);

        while (rs.next()) {
            Subinterest s = new Subinterest();
            s.setSubInterestId(rs.getInt("sub_interest_id"));
            s.setSubInterestName(rs.getString("name"));
            s.setInterestId(rs.getInt("interest_id"));
            sList.add(s);
        }
        return Collections.unmodifiableList(sList);
    }

}
