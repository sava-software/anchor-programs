package software.sava.anchor.programs.metadao.autocrat.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record InitializeProposalEvent(CommonFields common,
                                      PublicKey proposal,
                                      PublicKey dao,
                                      PublicKey question,
                                      PublicKey quoteVault,
                                      PublicKey baseVault,
                                      PublicKey passAmm,
                                      PublicKey failAmm,
                                      PublicKey passLpMint,
                                      PublicKey failLpMint,
                                      PublicKey proposer,
                                      long nonce,
                                      int number,
                                      long passLpTokensLocked,
                                      long failLpTokensLocked,
                                      int pdaBump,
                                      ProposalInstruction instruction) implements Borsh {

  public static InitializeProposalEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var common = CommonFields.read(_data, i);
    i += Borsh.len(common);
    final var proposal = readPubKey(_data, i);
    i += 32;
    final var dao = readPubKey(_data, i);
    i += 32;
    final var question = readPubKey(_data, i);
    i += 32;
    final var quoteVault = readPubKey(_data, i);
    i += 32;
    final var baseVault = readPubKey(_data, i);
    i += 32;
    final var passAmm = readPubKey(_data, i);
    i += 32;
    final var failAmm = readPubKey(_data, i);
    i += 32;
    final var passLpMint = readPubKey(_data, i);
    i += 32;
    final var failLpMint = readPubKey(_data, i);
    i += 32;
    final var proposer = readPubKey(_data, i);
    i += 32;
    final var nonce = getInt64LE(_data, i);
    i += 8;
    final var number = getInt32LE(_data, i);
    i += 4;
    final var passLpTokensLocked = getInt64LE(_data, i);
    i += 8;
    final var failLpTokensLocked = getInt64LE(_data, i);
    i += 8;
    final var pdaBump = _data[i] & 0xFF;
    ++i;
    final var instruction = ProposalInstruction.read(_data, i);
    return new InitializeProposalEvent(common,
                                       proposal,
                                       dao,
                                       question,
                                       quoteVault,
                                       baseVault,
                                       passAmm,
                                       failAmm,
                                       passLpMint,
                                       failLpMint,
                                       proposer,
                                       nonce,
                                       number,
                                       passLpTokensLocked,
                                       failLpTokensLocked,
                                       pdaBump,
                                       instruction);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(common, _data, i);
    proposal.write(_data, i);
    i += 32;
    dao.write(_data, i);
    i += 32;
    question.write(_data, i);
    i += 32;
    quoteVault.write(_data, i);
    i += 32;
    baseVault.write(_data, i);
    i += 32;
    passAmm.write(_data, i);
    i += 32;
    failAmm.write(_data, i);
    i += 32;
    passLpMint.write(_data, i);
    i += 32;
    failLpMint.write(_data, i);
    i += 32;
    proposer.write(_data, i);
    i += 32;
    putInt64LE(_data, i, nonce);
    i += 8;
    putInt32LE(_data, i, number);
    i += 4;
    putInt64LE(_data, i, passLpTokensLocked);
    i += 8;
    putInt64LE(_data, i, failLpTokensLocked);
    i += 8;
    _data[i] = (byte) pdaBump;
    ++i;
    i += Borsh.write(instruction, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(common)
         + 32
         + 32
         + 32
         + 32
         + 32
         + 32
         + 32
         + 32
         + 32
         + 32
         + 8
         + 4
         + 8
         + 8
         + 1
         + Borsh.len(instruction);
  }
}
