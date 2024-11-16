package software.sava.anchor.programs.jupiter.governance.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ProposalCreateEvent(PublicKey governor,
                                  PublicKey proposal,
                                  PublicKey proposer,
                                  int proposalType,
                                  int maxOption,
                                  long index,
                                  ProposalInstruction[] instructions) implements Borsh {

  public static ProposalCreateEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var governor = readPubKey(_data, i);
    i += 32;
    final var proposal = readPubKey(_data, i);
    i += 32;
    final var proposer = readPubKey(_data, i);
    i += 32;
    final var proposalType = _data[i] & 0xFF;
    ++i;
    final var maxOption = _data[i] & 0xFF;
    ++i;
    final var index = getInt64LE(_data, i);
    i += 8;
    final var instructions = Borsh.readVector(ProposalInstruction.class, ProposalInstruction::read, _data, i);
    return new ProposalCreateEvent(governor,
                                   proposal,
                                   proposer,
                                   proposalType,
                                   maxOption,
                                   index,
                                   instructions);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    governor.write(_data, i);
    i += 32;
    proposal.write(_data, i);
    i += 32;
    proposer.write(_data, i);
    i += 32;
    _data[i] = (byte) proposalType;
    ++i;
    _data[i] = (byte) maxOption;
    ++i;
    putInt64LE(_data, i, index);
    i += 8;
    i += Borsh.writeVector(instructions, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 32
         + 32
         + 32
         + 1
         + 1
         + 8
         + Borsh.lenVector(instructions);
  }
}
