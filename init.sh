#!/bin/sh
#================================================================
# HEADER
#================================================================
#% SYNOPSIS
#+    ${SCRIPT_NAME} [-hv] [-o[file] email
#%
#% DESCRIPTION
#%    Script to initialise basic auth and ssl certs for the nginx proxy.
#%
#% OPTIONS
#%    -o [file]                     Set log file (default=/dev/null)
#%                                  use DEFAULT keyword to autoname file
#%                                  The default value is /dev/null
#%    -h                            Print this help
#%    -v                            Print script information
#%
#% EXAMPLES
#%    ${SCRIPT_NAME} -o DEFAULT email
#%
#================================================================
#- IMPLEMENTATION
#-    version         ${SCRIPT_NAME} 0.0.1
#-    author          Jamie McDonald
#-    copyright       Copyright (c) Jamie McDonald
#-    license         MIT
#-    script_id       00001
#-
#================================================================
#  HISTORY
#     2019/02/15 : jrmcdonald : Script creation
# 
#================================================================
#  DEBUG OPTION
#    set -n  # Uncomment to check your syntax, without execution.
#    set -x  # Uncomment to debug this shell script
#
#================================================================
# END_OF_HEADER
#================================================================

trap 'error "FATAL ERROR at $(format_date): Interrupt signal intercepted! Exiting now..."
    2>&1 | tee -a ${fileLog:-/dev/null} >&2 ;
    exit 99;' INT QUIT TERM
trap 'cleanup' EXIT

#============================
#  TEMPLATE FUNCTIONS
#============================

usage() { printf "Usage: "; head -${SCRIPT_HEADSIZE:-99} ${0} | grep -e "^#+" | sed -e "s/^#+[ ]*//g" -e "s/\${SCRIPT_NAME}/${SCRIPT_NAME}/g" ; }
usagefull() { head -${SCRIPT_HEADSIZE:-99} ${0} | grep -e "^#[%+-]" | sed -e "s/^#[%+-]//g" -e "s/\${SCRIPT_NAME}/${SCRIPT_NAME}/g" ; }
scriptinfo() { head -${SCRIPT_HEADSIZE:-99} ${0} | grep -e "^#-" | sed -e "s/^#-//g" -e "s/\${SCRIPT_NAME}/${SCRIPT_NAME}/g"; }

format_date() { date "+%Y-%m-%d %H:%M:%S"; }
fecho () { level=${1}; shift; echo "[$(format_date)] [${level}] ${*}"; }

info() { fecho INFO "${*}"; }
warn() { fecho WARN "${*}"; }
error() { fecho ERROR "${*}"; }

exec_cmd() { ${*} ; rc_assert "Command failed: ${*}" ; return $rc; }
rc_assert() { [ $rc -ne 0 ] && error "${*} (RC=$rc)"; }

scriptstart() { info "rSstart at $(format_date) with process id ${EXEC_ID} by ${USER}@${HOSTNAME}:${PWD}" $( [ ${flagOptLog} -eq 1 ] && echo " (LOG: ${fileLog})" || echo " (NOLOG)" ); }
scriptfinish() { [ $rc -eq 0 ] && endType="INFO" || endType="ERROR"; fecho ${endType} "Finished at $(format_date) (RC=$rc)."; exit $rc; }
cleanup() { info "Cleaning up"; }

check_cre_file() {
    myFile=${1}
    [ "${myFile}" = "/dev/null" ] && return 0
    [ -e ${myFile} ] && error "${myFile}: File already exists" && return 1
    touch ${myFile} 2>&1 1>/dev/null
    [ $? -ne 0 ] && error "${myFile}: Cannot create file" && return 2
    rm -f ${myFile} 2>&1 1>/dev/null
    [ $? -ne 0 ] && error "${myFile}: Cannot delete file" && return 3
    return 0
}

#============================
#  TEMPLATE VARIABLES
#============================

SCRIPT_HEADSIZE=$(grep -sn "^# END_OF_HEADER" ${0} | head -1 | cut -f1 -d:)
SCRIPT_ID="$(scriptinfo | grep script_id | tr -s ' ' | cut -d' ' -f3)"
SCRIPT_NAME="$(basename ${0})" 
SCRIPT_UNIQ="${SCRIPT_NAME%.*}.${SCRIPT_ID}.${HOSTNAME%%.*}"
SCRIPT_UNIQ_DATED="${SCRIPT_UNIQ}.$(date "+%y%m%d%H%M%S").${$}"
SCRIPT_DIR="$( cd $(dirname "$0") && pwd )"
SCRIPT_DIR_TEMP="/tmp" 

HOSTNAME="$(hostname)"
FULL_COMMAND="${0} $*"
EXEC_DATE=$(date "+%y%m%d%H%M%S")
EXEC_ID=${$}

fileLog="/dev/null"
rc=0

flagOptErr=0
flagOptLog=0

#============================
#  OPTS PARSE
#============================

SCRIPT_OPTS='o:hv'

while getopts ${SCRIPT_OPTS} OPTION ; do
    case "$OPTION" in
        o ) fileLog="${OPTARG}"
            [ "${OPTARG}" = *"DEFAULT" ] && fileLog="$( echo ${OPTARG} | sed -e "s/DEFAULT/${SCRIPT_UNIQ_DATED}.log/g" )"
            flagOptLog=1
        ;;
        h ) usagefull
            exit 0
        ;;
        v ) scriptinfo
            exit 0
        ;;
        ? ) error "-$OPTARG: unknown option"
            flagOptErr=1
        ;;
    esac
done

#============================
# SCRIPT SETUP
#============================

[ $flagOptErr -eq 1 ] && usage 1>&2 && exit 1

check_cre_file ${fileLog} || exit 3
[ "${fileLog}" != "/dev/null" ] && touch ${fileLog} && fileLog="$( cd $(dirname "${fileLog}") && pwd )"/"$(basename ${fileLog})"

#============================
# ARGS & SETUP
#============================

[ $# -gt 1 ] && error "Too many arguments" && usage 1>&2 && exit 2
[ -z "${1}" ] && error "Email argument required" && usage 1>&2 && exit 2

ARG_EMAIL=${1} 

{ trap 'kill -TERM ${$}; exit 99;' TERM

scriptstart
    
#============================
# MAIN SCRIPT START
#============================

certs_volume_name="app_certs_data"
proxy_name="app_proxy_1"

domain="podcasts.qwyck.net"
path="/etc/letsencrypt/live/${domain}"
rsa_key_size=4096

info "Creating certificates volume"
exec_cmd "docker volume create --name ${certs_volume_name}"

info "Creating certificates for ${domain}"
domain_arg="-d ${domain}"

exec_cmd "docker run -it --rm -p 80:80 -v ${certs_volume_name}:/etc/letsencrypt certbot/certbot certonly --standalone --email ${ARG_EMAIL} ${domain_arg} --rsa-key-size ${rsa_key_size} --agree-tos --no-eff-email --force-renewal"

info "Starting containers"
exec_cmd "wget -N https://raw.githubusercontent.com/jrmcdonald/podcasts-server/master/docker-compose.yml"
exec_cmd "docker-compose up -d"

info "Reading nginx .htpasswd"
printf "Enter nginx .htpasswd user: "
read htpasswd_user

stty -echo
printf "Enter nginx .htpasswd password: "
read htpasswd_password
stty echo
echo

info "Setting nginx .htpasswd"
exec_cmd "docker exec ${proxy_name} htpasswd -c -B -b /etc/apache2/.htpasswd ${htpasswd_user} ${htpasswd_password}"

info "Reloading nginx"
exec_cmd "docker exec ${proxy_name} nginx -s reload"

#============================
# MAIN SCRIPT END
#============================

scriptfinish ; } 2>&1 | tee ${fileLog}
exit $rc
