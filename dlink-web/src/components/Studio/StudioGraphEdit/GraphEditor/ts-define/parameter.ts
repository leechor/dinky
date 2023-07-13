export type Parameter = {
  code: string,
  name: string;
  group: string;
  feature: {
    icon: string;
  };
  specification: string;
  ports: { inputs: any; outputs: any };
};
