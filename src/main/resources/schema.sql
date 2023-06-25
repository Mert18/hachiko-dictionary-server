--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3
-- Dumped by pg_dump version 15.3

-- Started on 2023-06-25 13:03:23

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 214 (class 1259 OID 16610)
-- Name: accounts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.accounts (
    id character varying(255) NOT NULL,
    confirmed boolean NOT NULL,
    email character varying(255),
    password character varying(255),
    role character varying(255),
    username character varying(255)
);


ALTER TABLE public.accounts OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16617)
-- Name: confirmations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.confirmations (
    id character varying(255) NOT NULL,
    email character varying(255),
    token character varying(255)
);


ALTER TABLE public.confirmations OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16624)
-- Name: learned_words; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.learned_words (
    level integer NOT NULL,
    account_id character varying(255) NOT NULL,
    word_id character varying(255) NOT NULL
);


ALTER TABLE public.learned_words OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16631)
-- Name: quizzes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.quizzes (
    id character varying(255) NOT NULL,
    correct_answers integer NOT NULL,
    difficulty character varying(255),
    incorrect_answers integer NOT NULL,
    account_id character varying(255) NOT NULL
);


ALTER TABLE public.quizzes OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16638)
-- Name: quotes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.quotes (
    id character varying(255) NOT NULL,
    author character varying(255),
    difficulty character varying(255),
    quote character varying(255)
);


ALTER TABLE public.quotes OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16645)
-- Name: word_antonyms; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.word_antonyms (
    word_id character varying(255) NOT NULL,
    antonyms character varying(255)
);


ALTER TABLE public.word_antonyms OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16650)
-- Name: word_descriptions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.word_descriptions (
    word_id character varying(255) NOT NULL,
    descriptions character varying(255)
);


ALTER TABLE public.word_descriptions OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16655)
-- Name: word_sentences; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.word_sentences (
    word_id character varying(255) NOT NULL,
    sentences character varying(255)
);


ALTER TABLE public.word_sentences OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16660)
-- Name: word_synonyms; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.word_synonyms (
    word_id character varying(255) NOT NULL,
    synonyms character varying(255)
);


ALTER TABLE public.word_synonyms OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16665)
-- Name: words; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.words (
    id character varying(255) NOT NULL,
    difficulty character varying(255),
    kind character varying(255),
    title character varying(255)
);


ALTER TABLE public.words OWNER TO postgres;

--
-- TOC entry 3209 (class 2606 OID 16616)
-- Name: accounts accounts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT accounts_pkey PRIMARY KEY (id);


--
-- TOC entry 3215 (class 2606 OID 16623)
-- Name: confirmations confirmations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.confirmations
    ADD CONSTRAINT confirmations_pkey PRIMARY KEY (id);


--
-- TOC entry 3219 (class 2606 OID 16630)
-- Name: learned_words learned_words_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.learned_words
    ADD CONSTRAINT learned_words_pkey PRIMARY KEY (account_id, word_id);


--
-- TOC entry 3221 (class 2606 OID 16637)
-- Name: quizzes quizzes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.quizzes
    ADD CONSTRAINT quizzes_pkey PRIMARY KEY (id);


--
-- TOC entry 3223 (class 2606 OID 16644)
-- Name: quotes quotes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.quotes
    ADD CONSTRAINT quotes_pkey PRIMARY KEY (id);


--
-- TOC entry 3217 (class 2606 OID 16677)
-- Name: confirmations uk_jlm8g0c4gfdw7l8xfaikx6f7w; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.confirmations
    ADD CONSTRAINT uk_jlm8g0c4gfdw7l8xfaikx6f7w UNIQUE (email);


--
-- TOC entry 3211 (class 2606 OID 16675)
-- Name: accounts uk_k8h1bgqoplx0rkngj01pm1rgp; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT uk_k8h1bgqoplx0rkngj01pm1rgp UNIQUE (username);


--
-- TOC entry 3213 (class 2606 OID 16673)
-- Name: accounts uk_n7ihswpy07ci568w34q0oi8he; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT uk_n7ihswpy07ci568w34q0oi8he UNIQUE (email);


--
-- TOC entry 3225 (class 2606 OID 16679)
-- Name: quotes uk_pvgp6simx3am1bi880mrei09q; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.quotes
    ADD CONSTRAINT uk_pvgp6simx3am1bi880mrei09q UNIQUE (quote);


--
-- TOC entry 3227 (class 2606 OID 16671)
-- Name: words words_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.words
    ADD CONSTRAINT words_pkey PRIMARY KEY (id);


--
-- TOC entry 3230 (class 2606 OID 16690)
-- Name: quizzes fk31eong221h2fv58hu3wo94833; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.quizzes
    ADD CONSTRAINT fk31eong221h2fv58hu3wo94833 FOREIGN KEY (account_id) REFERENCES public.accounts(id);


--
-- TOC entry 3228 (class 2606 OID 16680)
-- Name: learned_words fk7xnj4qfwt3s0aimswikpf0vhr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.learned_words
    ADD CONSTRAINT fk7xnj4qfwt3s0aimswikpf0vhr FOREIGN KEY (account_id) REFERENCES public.accounts(id);


--
-- TOC entry 3232 (class 2606 OID 16700)
-- Name: word_descriptions fk8w8dgxruf0urdljqbt2r3f5r5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.word_descriptions
    ADD CONSTRAINT fk8w8dgxruf0urdljqbt2r3f5r5 FOREIGN KEY (word_id) REFERENCES public.words(id);


--
-- TOC entry 3234 (class 2606 OID 16710)
-- Name: word_synonyms fkdm5ine73363r5db80gpo9p22i; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.word_synonyms
    ADD CONSTRAINT fkdm5ine73363r5db80gpo9p22i FOREIGN KEY (word_id) REFERENCES public.words(id);


--
-- TOC entry 3229 (class 2606 OID 16685)
-- Name: learned_words fkgwx7t1sdf1us2r0gj15hflblt; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.learned_words
    ADD CONSTRAINT fkgwx7t1sdf1us2r0gj15hflblt FOREIGN KEY (word_id) REFERENCES public.words(id);


--
-- TOC entry 3231 (class 2606 OID 16695)
-- Name: word_antonyms fkng7v7lmuyw2tbccfm0568ffpb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.word_antonyms
    ADD CONSTRAINT fkng7v7lmuyw2tbccfm0568ffpb FOREIGN KEY (word_id) REFERENCES public.words(id);


--
-- TOC entry 3233 (class 2606 OID 16705)
-- Name: word_sentences fkqeloe94eqlu3tp9j6vk658vbu; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.word_sentences
    ADD CONSTRAINT fkqeloe94eqlu3tp9j6vk658vbu FOREIGN KEY (word_id) REFERENCES public.words(id);


-- Completed on 2023-06-25 13:03:24

--
-- PostgreSQL database dump complete
--

