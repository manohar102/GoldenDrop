FROM httpd:alpine

# Update commands
RUN apk update
RUN apk upgrade 
RUN rm -rf /var/cache/apk/*

# Copy required conf files
COPY httpd.conf /usr/local/apache2/conf/httpd.conf
COPY rules.conf /usr/local/apache2/conf.d/
COPY ssl.conf /usr/local/apache2/conf.d/
COPY common.conf /usr/local/apache2/conf.d/

# Copy certificate files
RUN mkdir -p /usr/local/apache2/ssl/
COPY server.crt /usr/local/apache2/ssl/
COPY server.key /usr/local/apache2/ssl/

# Set environment variable
ENV IS_DOCKER_ENV=true

# User and Group
ARG USER=atuser
ARG GROUP=alwaystech

# Create a user group 'alwaystech'
RUN addgroup -S $GROUP

# Create a user under group
RUN adduser -S -D -h /usr/app/src $USER $GROUP

# Chown all the files to the app user.
RUN chown -R $USER:$GROUP /usr/local/apache2/logs/

EXPOSE 443
# Switch to '$USER'
USER $USER
