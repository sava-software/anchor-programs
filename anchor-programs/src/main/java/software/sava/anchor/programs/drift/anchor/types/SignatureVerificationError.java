package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum SignatureVerificationError implements Borsh.Enum {

  InvalidEd25519InstructionProgramId,
  InvalidEd25519InstructionDataLength,
  InvalidSignatureIndex,
  InvalidSignatureOffset,
  InvalidPublicKeyOffset,
  InvalidMessageOffset,
  InvalidMessageDataSize,
  InvalidInstructionIndex,
  MessageOffsetOverflow,
  InvalidMessageHex,
  InvalidMessageData,
  LoadInstructionAtFailed;

  public static SignatureVerificationError read(final byte[] _data, final int offset) {
    return Borsh.read(SignatureVerificationError.values(), _data, offset);
  }
}