alter table if exists pastebin.imagerPosts
add constraint FK
foreign key (author)
references pastebin.users (email)
