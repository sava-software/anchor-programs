package software.sava.anchor.programs.glam.spl.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

// An integration program can have multiple protocols supported.
// Enabled protocols are stored in a bitmask, and each protocol can have its own policy.
public record IntegrationAcl(PublicKey integrationProgram,
                             int protocolsBitmask,
                             ProtocolPolicy[] protocolPolicies) implements Borsh {

  public static IntegrationAcl read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var integrationProgram = readPubKey(_data, i);
    i += 32;
    final var protocolsBitmask = getInt16LE(_data, i);
    i += 2;
    final var protocolPolicies = Borsh.readVector(ProtocolPolicy.class, ProtocolPolicy::read, _data, i);
    return new IntegrationAcl(integrationProgram, protocolsBitmask, protocolPolicies);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    integrationProgram.write(_data, i);
    i += 32;
    putInt16LE(_data, i, protocolsBitmask);
    i += 2;
    i += Borsh.writeVector(protocolPolicies, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 32 + 2 + Borsh.lenVector(protocolPolicies);
  }
}
