PGDMP  2    3                |            todo    16.4    16.4     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16780    todo    DATABASE     w   CREATE DATABASE todo WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Japanese_Japan.932';
    DROP DATABASE todo;
                postgres    false            �            1259    16782    todolist    TABLE     �   CREATE TABLE public.todolist (
    id integer NOT NULL,
    todo character varying(200),
    date character varying(16),
    datecompletion character varying(16),
    status integer,
    dateend character varying(30)
);
    DROP TABLE public.todolist;
       public         heap    postgres    false            �            1259    16781    todolist_id_seq    SEQUENCE     �   CREATE SEQUENCE public.todolist_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.todolist_id_seq;
       public          postgres    false    216            �           0    0    todolist_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.todolist_id_seq OWNED BY public.todolist.id;
          public          postgres    false    215            P           2604    16785    todolist id    DEFAULT     j   ALTER TABLE ONLY public.todolist ALTER COLUMN id SET DEFAULT nextval('public.todolist_id_seq'::regclass);
 :   ALTER TABLE public.todolist ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    215    216    216            �          0    16782    todolist 
   TABLE DATA           S   COPY public.todolist (id, todo, date, datecompletion, status, dateend) FROM stdin;
    public          postgres    false    216   �
       �           0    0    todolist_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.todolist_id_seq', 1, false);
          public          postgres    false    215            R           2606    16787    todolist todolist_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.todolist
    ADD CONSTRAINT todolist_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.todolist DROP CONSTRAINT todolist_pkey;
       public            postgres    false    216            �     x�}�ON1���)� �_�/콀a�e�&�C �
\�3,FH�(�^�3m���ә�iҗ�}y-��0S�QB�6�6\�r��*8F-�-�z��S�^�2�o3��̿�D2:��A]���W���K�?-�c[*-�-���H-7r�Vi�Ҥ�rm�Sw��塛�b�����>�,�ю�D���7��� �ɠ�?<�>���A[�������6�;��A�`,�s&�c+ߣ|_�~Z�a �mC�2����3Z]$C�]c�� ���E     