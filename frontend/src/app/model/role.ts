export interface Role {
  id: number;
  roleName: RoleName;
}

export enum RoleName {
  ADMIN = 'ADMIN',
  USER = 'USER'
}

export const StatusMapping = {
  [RoleName.ADMIN]: "ADMIN",
  [RoleName.USER]: "USER",
}
