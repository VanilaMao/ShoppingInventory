import React,{useState,useEffect} from 'react';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import Avatar from '@material-ui/core/Avatar';
import { red } from '@material-ui/core/colors';
import IconButton from '@material-ui/core/IconButton';
import CardActions from '@material-ui/core/CardActions';
import MoreVertIcon from '@material-ui/icons/MoreVert';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import Typography from '@material-ui/core/Typography';
import CardContent from '@material-ui/core/CardContent';
import {connect} from 'react-redux'
import {createNewGroup,applyToBeDoctor,loadUser,joinGroup} from '../../redux/actions/actions'

import MuiDialogTitle from '@material-ui/core/DialogTitle';
import Dialog from '@material-ui/core/Dialog';
import CloseIcon from '@material-ui/icons/Close';
import Button from '@material-ui/core/Button';
import MuiDialogContent from '@material-ui/core/DialogContent';
import MuiDialogActions from '@material-ui/core/DialogActions';

import {TextField} from '@material-ui/core'

import './dashboard.css'


const DialogTitle = ({onClose, title})=>{
    return (
        <MuiDialogTitle disableTypography className='dialog-root'>
            <Typography variant="h6" className='dialog-title'>{title}</Typography>
            {onClose ? (
                    <IconButton aria-label="close"  onClick={()=>onClose('')} className='close-button'>
                    <CloseIcon />
                    </IconButton>
                ) : null}
        </MuiDialogTitle>
    )
}

//Create a new group for a doctor, then you can  accept nurse's application to join in this group

const CreateGroupModal = ({openStatus,handleClose,buttonContent,content,title})=>{
    const [groupName, setGroupName] = useState('');
    return (
        <Dialog onClose={()=>handleClose('')} aria-labelledby="customized-dialog-title" open={openStatus}>
        <DialogTitle id="customized-dialog-title" onClose={handleClose} title={title}/>
        <MuiDialogContent dividers>
          <Typography gutterBottom>
           {content}
          </Typography>
          <Typography gutterBottom>
                <TextField required label="Group Name" onChange={(e)=>setGroupName(e.target.value)}></TextField>
          </Typography>
          
        </MuiDialogContent>
        <MuiDialogActions>
          <Button autoFocus onClick={()=>handleClose(groupName)}  color="primary">
                {buttonContent}
          </Button>
        </MuiDialogActions>
      </Dialog>
    )
}

const UserCard =  ({userInfo, roles, userGroups,doctorGroups,applyToBeDoctor,createNewGroup,loadUser,joinGroup}) => {
    const [anchorEl, setAnchorEl] = useState(null);
    const doctor = roles.includes('Doctor');
    const [openGroupModal,setOpenGroupModal] = useState(false)
    const [buttonContent,setButtonContent] = useState('');
    const [content,setContent] = useState('');
    const [createGroup, setCreateGroup] = useState(false)
    const [title,setTitile] = useState('')

    console.log(roles)

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
      };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const createDoctorGroup = ()=>{
        if(doctor){
            setOpenGroupModal(true)
            setContent("Create a new group for a doctor, then you can  accept nurse's application to join in this group");
            setButtonContent("Create")
            setCreateGroup(true)
            setTitile("Create a New Group")
        }
        handleClose()
    }

    const handleJoinGroup = ()=>{
        setOpenGroupModal(true);
        setContent("Join in a doctor's group, then you can recieve offer, confirm tracking and request payment");
        setButtonContent("Join")
        setCreateGroup(false)
        setTitile("Join a Doctor's Group")
        handleClose()
    }

    const onCloseGrouoModal =(groupName)=>{
        setOpenGroupModal(false)
        if(groupName){
            if(createGroup){
                createNewGroup(groupName)
            }else {
                joinGroup(groupName)
            }
        }
    }

    const applyDoctor = ()=>{
        if(!doctor){
            applyToBeDoctor()
        }
        handleClose()
    }

    useEffect(()=>{
        loadUser()
    },[])

    const userTitle = `${userInfo.name.toUpperCase()}, Welcome to the Inventory and Shopping System`;

    const doctorGroupsList = doctorGroups?doctorGroups.map(group=>
            <li key={group.name}>
                {`name: ${group.name}, status: ${group.status}`}
            </li>):null;

    const userGroupsList = userGroups?userGroups.map(userGroup=>
        <li key={userGroup.name}>
            {`name: ${userGroup.name}, status: ${userGroup.groupApplicationStatus}`}
        </li>):null;

    return (
        <div>
            <CreateGroupModal openStatus={openGroupModal}
                content={content}
                title={title}
                buttonContent={buttonContent}
                handleClose={onCloseGrouoModal}/>
            <Card>
                <CardHeader
                    avatar = {
                        <Avatar aria-label="recipe" className="avatar" >
                            D
                        </Avatar>
                    }
                    action ={
                        <IconButton aria-label="settings" onClick={handleClick}>
                            <MoreVertIcon >
                                
                            </MoreVertIcon>
                        </IconButton>
                        
                    }
                    title = {userTitle}
                    subheader = {`Role: ${roles.join(",")}`}
                />
                <Menu id="setting-menu"
                    anchorEl={anchorEl}
                    keepMounted
                    open={Boolean(anchorEl)}
                    onClose={handleClose}
                >
                    {doctor?<MenuItem onClick={createDoctorGroup}>Create a New Docotor Group</MenuItem>:<MenuItem onClick={applyDoctor}>Apply To Be A Doctor</MenuItem>}
                    <MenuItem onClick={handleJoinGroup}>Join a Doctor Group</MenuItem>                      
                </Menu>  
                <CardContent>
                    {doctor?
                            <Typography  gutterBottom>
                                <div>
                                <p>Groups you have:</p>
                                {doctorGroupsList}
                                </div>
                            </Typography>:null}
                           
                    <Typography gutterBottom>Groups you applied to Join in:</Typography>
                    {userGroupsList}
                    <Typography paragraph>Unpaid total:</Typography>
                    <Typography paragraph>Offer not confirmed:</Typography>
                    <Typography paragraph>Payment not confirmed</Typography>
                </CardContent>
                <CardActions>
                </CardActions>                
            </Card>
        </div>
      
    );
}

const mapStateToProps = (state)=>(
    {
        userInfo : state.user.userInfo,
        roles: state.user.roles,
        userGroups: state.user.userGroups,
        doctorGroups:state.user.doctorGroups
    }
)

const mapDispatchToProps = {
    createNewGroup: (name) =>createNewGroup(name),
    loadUser,
    applyToBeDoctor,
    joinGroup: (name)=>joinGroup(name)
}

export default connect(mapStateToProps,mapDispatchToProps)(UserCard);