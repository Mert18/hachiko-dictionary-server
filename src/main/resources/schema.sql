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

CREATE TABLE IF NOT EXISTS public.accounts (
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

CREATE TABLE IF NOT EXISTS public.confirmations (
    id character varying(255) NOT NULL,
    email character varying(255),
    token character varying(255)
);


ALTER TABLE public.confirmations OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16624)
-- Name: learned_words; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE IF NOT EXISTS public.learned_words (
    level integer NOT NULL,
    account_id character varying(255) NOT NULL,
    word_id character varying(255) NOT NULL
);


ALTER TABLE public.learned_words OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16631)
-- Name: quizzes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE IF NOT EXISTS public.quizzes (
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

CREATE TABLE IF NOT EXISTS public.quotes (
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

CREATE TABLE IF NOT EXISTS public.word_antonyms (
    word_id character varying(255) NOT NULL,
    antonyms character varying(255)
);


ALTER TABLE public.word_antonyms OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16650)
-- Name: word_descriptions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE IF NOT EXISTS public.word_descriptions (
    word_id character varying(255) NOT NULL,
    descriptions character varying(255)
);


ALTER TABLE public.word_descriptions OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16655)
-- Name: word_sentences; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE IF NOT EXISTS public.word_sentences (
    word_id character varying(255) NOT NULL,
    sentences character varying(255)
);


ALTER TABLE public.word_sentences OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16660)
-- Name: word_synonyms; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE IF NOT EXISTS public.word_synonyms (
    word_id character varying(255) NOT NULL,
    synonyms character varying(255)
);


ALTER TABLE public.word_synonyms OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16665)
-- Name: words; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE IF NOT EXISTS public.words (
    id character varying(255) NOT NULL,
    difficulty character varying(255),
    kind character varying(255),
    title character varying(255)
);


ALTER TABLE public.words OWNER TO postgres;

-- Completed on 2023-06-25 13:03:24

--
-- PostgreSQL database dump complete
--

